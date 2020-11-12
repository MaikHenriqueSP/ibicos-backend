package br.com.ibicos.ibicos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.ibicos.ibicos.dto.CredentialsDTO;
import br.com.ibicos.ibicos.dto.TokenDTO;
import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.service.JwtService;
import br.com.ibicos.ibicos.service.UserDetailsServiceImpl;
import br.com.ibicos.ibicos.service.UserService;

@RestController
@RequestMapping
public class UserController {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/signUp")
	public User save(@RequestBody User user) {
		String encodedPassword = passwordEncoder.encode(user.getPasswordUser());
		user.setPasswordUser(encodedPassword);
		return userService.save(user);
	}
		
	
	
	@PostMapping("/login")
	public TokenDTO authenticate(@RequestBody CredentialsDTO credentials) {
		try {
			User user = new User();
			user.setEmail(credentials.getEmail());
			user.setPasswordUser(credentials.getPasswordUser());
			
			userDetailsServiceImpl.authenticate(user);
			String token = jwtService.generateToken(user);
			
			return new TokenDTO(user.getEmail(), token);
		} catch (UsernameNotFoundException | BadCredentialsException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

}


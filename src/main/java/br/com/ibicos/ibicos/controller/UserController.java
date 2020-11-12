package br.com.ibicos.ibicos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibicos.ibicos.dto.CredentialsDTO;
import br.com.ibicos.ibicos.dto.TokenDTO;
import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.service.IUserService;
import br.com.ibicos.ibicos.service.JwtService;
import br.com.ibicos.ibicos.service.UserDetailsServiceImpl;

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
	private IUserService userService;
	
	
	@PostMapping("/signUp")
	public ResponseEntity<?> save(@RequestBody User user) {
		String encodedPassword = passwordEncoder.encode(user.getPasswordUser());
		user.setPasswordUser(encodedPassword);
		User savedUser = userService.save(user);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}
	
	@PostMapping("/login")
	public  ResponseEntity<?> authenticate(@RequestBody CredentialsDTO credentials) {
		User user = new User();
		
		user.setEmail(credentials.getEmail());
		user.setPasswordUser(credentials.getPasswordUser());
			
		userDetailsServiceImpl.authenticate(user);
		String token = jwtService.generateToken(user);			
		return ResponseEntity.ok(new TokenDTO(user.getEmail(), token));
	}

}


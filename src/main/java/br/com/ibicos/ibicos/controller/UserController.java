package br.com.ibicos.ibicos.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibicos.ibicos.dto.CredentialsDTO;
import br.com.ibicos.ibicos.dto.EmailDTO;
import br.com.ibicos.ibicos.dto.RecoveryDTO;
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
	private JwtService jwtService;
	
	@Autowired
	private IUserService userService;
	
	
	@PostMapping("/signUp")
	public ResponseEntity<?> save(@Valid @RequestBody User user) {
		User savedUser = userService.save(user);				
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}	
	
	@PostMapping("/login")
	public  ResponseEntity<?> authenticate(@RequestBody CredentialsDTO credentials) {
		userDetailsServiceImpl.authenticate(credentials);
		String token = jwtService.generateToken(credentials);
		
		return ResponseEntity.ok(new TokenDTO(credentials.getEmail(), token));
	}
	
	@GetMapping("/verify")
	public ResponseEntity<?> verifyAccount(@RequestParam("token") String validationToken) {
		System.out.println(validationToken);
		User verifiedUser = userService.verifyAccount(validationToken);
		return new ResponseEntity<>(verifiedUser, HttpStatus.OK);
	}
	
	@PostMapping("/resetPassword/request")
	public ResponseEntity<?> resetPasswordRequest(@RequestBody EmailDTO emailDTO) {
		
		userService.resetPasswordProcessing(emailDTO.getEmail());
		return ResponseEntity.ok().body("An email with the reset password instructions was sent to: '" + emailDTO.getEmail() 
				+ "', please check it and follow the provided instructions to reset your email!");
	}
	
	@PostMapping("/resetPassword/change")
	public ResponseEntity<?> changeUserPassword(@RequestBody RecoveryDTO recoveryDTO) {
		userService.changeUserPassword(recoveryDTO.getAccountRecoveryToken(), recoveryDTO.getNewPassword());
		return ResponseEntity.ok().body("Password successfully changed");				
	}
	
	
	
}


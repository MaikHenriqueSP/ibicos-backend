package br.com.ibicos.ibicos.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

		Map<String, Object> responseMap = Map.of("message",
				"Your account was successfully signed up, but before using it you need to confirm it using your email so please check it!",
				"savedUser", savedUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody CredentialsDTO credentials) {
		userDetailsServiceImpl.authenticate(credentials);
		String token = jwtService.generateToken(credentials);

		return ResponseEntity.ok(new TokenDTO(token));
	}

	@GetMapping("/verify")
	public ResponseEntity<?> verifyAccount(@RequestParam("token") String validationToken) {
		userService.verifyAccountRequestHandler(validationToken);
		return new ResponseEntity<>(Map.of("message", "The account is now verified and you are now able to login and use it"), HttpStatus.OK);
	}

	@PostMapping("/resetPassword/request")
	public ResponseEntity<?> resetPasswordRequestHandler(@RequestBody EmailDTO emailDTO) {

		userService.resetPasswordRequestHandler(emailDTO.getEmail());
		return ResponseEntity.ok().body(
				Map.of("message", "An email with the reset password instructions was sent to: '" + emailDTO.getEmail()
						+ "', please check it and follow the provided instructions to reset your email!"));
	}

	@PostMapping("/resetPassword/change")
	public ResponseEntity<?> changeUserPassword(@RequestBody RecoveryDTO recoveryDTO) {
		userService.changeUserPassword(recoveryDTO.getAccountRecoveryToken(), recoveryDTO.getNewPassword());
		return ResponseEntity.ok().body(Map.of("message", "Password successfully changed"));
	}

}

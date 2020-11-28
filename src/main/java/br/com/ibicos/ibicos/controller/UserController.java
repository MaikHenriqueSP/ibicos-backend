package br.com.ibicos.ibicos.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.ibicos.ibicos.dto.CredentialsDTO;
import br.com.ibicos.ibicos.dto.RecoveryDTO;
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
				"savedUser", savedUser,
				"statusCode", HttpStatus.CREATED.value());
		return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
	}
	
	@GetMapping("/checkEmailInUser")
	public ResponseEntity<?> isEmailInUse(@RequestBody ObjectNode objectNode) {
		String email = objectNode.asText("email");
		Boolean isEmailInUse = userService.isEmailInUse(email);
		return isEmailInUse ? ResponseEntity.status(HttpStatus.CONFLICT)
				.body(Map.of("message", "Email already in use")) : 
					ResponseEntity.status(HttpStatus.OK)
					.body(Map.of("message", "Email free to use"));
		
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody CredentialsDTO credentials) {
		userDetailsServiceImpl.authenticate(credentials);
		String token = jwtService.generateToken(credentials);

		return ResponseEntity.ok(Map.of("token", token));
	}

	@GetMapping("/verify")
	public ResponseEntity<?> verifyAccount(@RequestParam("token") String validationToken) {
		userService.verifyAccountRequestHandler(validationToken);
		return new ResponseEntity<>(Map.of("message", "The account is now verified and you are now able to login and use it"), HttpStatus.OK);
	}

	@PostMapping("/resetPassword/request")
	public ResponseEntity<?> resetPasswordRequestHandler(@RequestBody ObjectNode objectNode) {
		String email = objectNode.get("email").asText();

		userService.resetPasswordRequestHandler(email);
		return ResponseEntity.ok().body(
				Map.of("message", "An email with the reset password instructions was sent to: '" + email
						+ "', please check it and follow the provided instructions to reset your email!"));
	}

	@PostMapping("/resetPassword/change")
	public ResponseEntity<?> changeUserPassword(@RequestBody RecoveryDTO recoveryDTO) {
		userService.changeUserPassword(recoveryDTO.getAccountRecoveryToken(), recoveryDTO.getNewPassword());
		return ResponseEntity.ok().body(Map.of("message", "Password successfully changed"));
	}
	
	@GetMapping("api/v1/user/profile/{idUser}")
	public ResponseEntity<?> showUserProfile(@PathVariable Integer idUser) {
		User user = userService.findUserById(idUser);
		return ResponseEntity.ok().body(
				Map.of("message", "User found with success",
						"user", user,
						"status", HttpStatus.OK.value())				
				);		
	} 
	
	@PutMapping("api/v1/user/profile/update")
	public ResponseEntity<?> updateUserProfile(@RequestBody User user) {
		return ResponseEntity.ok(userService.updateUser(user));
	}

}

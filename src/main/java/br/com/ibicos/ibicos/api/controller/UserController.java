package br.com.ibicos.ibicos.api.controller;

import br.com.ibicos.ibicos.dto.CredentialsDTO;
import br.com.ibicos.ibicos.dto.RecoveryDTO;
import br.com.ibicos.ibicos.domain.entity.User;
import br.com.ibicos.ibicos.domain.service.IUserService;
import br.com.ibicos.ibicos.domain.service.JwtService;
import br.com.ibicos.ibicos.domain.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping
public class UserController {

	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final JwtService jwtService;
	private final IUserService userService;

	public UserController(UserDetailsServiceImpl userDetailsServiceImpl, JwtService jwtService, IUserService userService) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
		this.jwtService = jwtService;
		this.userService = userService;
	}

	@PostMapping("/sign-up")
	public ResponseEntity<?> save(@Valid @RequestBody User user) {
		User savedUser = userService.save(user);

		Map<String, Object> responseMap = Map.of("message",
				"Your account was successfully signed up, but before using it you need to confirm it using your email so please check it!",
				"savedUser", savedUser,
				"statusCode", HttpStatus.CREATED.value());
		return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
	}
	
	@GetMapping("/check-email-in-use")
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

	@PostMapping("/reset-password/request")
	public ResponseEntity<?> resetPasswordRequestHandler(@RequestBody ObjectNode objectNode) {
		String email = objectNode.get("email").asText();

		userService.resetPasswordRequestHandler(email);
		return ResponseEntity.ok().body(
				Map.of("message", "An email with the reset password instructions was sent to: '" + email
						+ "', please check it and follow the provided instructions to reset your email!"));
	}

	@PostMapping("/reset-password/change")
	public ResponseEntity<?> changeUserPassword(@RequestBody RecoveryDTO recoveryDTO) {
		userService.changeUserPassword(recoveryDTO.getAccountRecoveryToken(), recoveryDTO.getNewPassword());
		return ResponseEntity.ok().body(Map.of("message", "Password successfully changed"));
	}
	
	@GetMapping("api/v1/users/profile/{idUser}")
	public ResponseEntity<?> showUserProfile(@PathVariable Integer idUser) {
		User user = userService.findUserById(idUser);
		return ResponseEntity.ok().body(
				Map.of("message", "User found with success",
						"user", user,
						"status", HttpStatus.OK.value())				
				);		
	} 
	
	@PutMapping("api/v1/users/profile/update")
	public ResponseEntity<?> updateUserProfile(@RequestBody User user) {
		return ResponseEntity.ok(userService.updateUser(user));
	}
	
	@GetMapping("api/v1/users/user-by-email")
	public ResponseEntity<?> getUserByEmail(@RequestBody ObjectNode objectNode) {
		String email = objectNode.get("email").asText();
		User user = userService.findUserByEmail(email);
		return ResponseEntity.ok(user);
	}

}

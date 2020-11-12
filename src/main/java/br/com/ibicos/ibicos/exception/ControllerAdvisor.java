package br.com.ibicos.ibicos.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler  {
		
	@ExceptionHandler(value = {UserAlreadyExistsException.class})
	public ResponseEntity<Object> handleUserAlreadyExistsExceptio(UserAlreadyExistsException exception) {
		ExceptionPayload exceptionPayload = ExceptionPayload.builder()
			.timestamp(LocalDateTime.now())
			.title("User already registered")
			.statusCode(HttpStatus.CONFLICT.value())
			.description(exception.getMessage())
			.build();
		
		return new ResponseEntity<>(exceptionPayload, HttpStatus.CONFLICT);
	}
	
	
	@ExceptionHandler(value = {InvalidEmailFormatException.class})
	public ResponseEntity<Object> handleException(InvalidEmailFormatException exception) {
		ExceptionPayload exceptionPayload = ExceptionPayload.builder()
			.timestamp(LocalDateTime.now())
			.title("Invalid email format")
			.statusCode(HttpStatus.CONFLICT.value())
			.description(exception.getMessage())
			.build();
		
		return new ResponseEntity<>(exceptionPayload, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = {UsernameNotFoundException.class, BadCredentialsException.class})
	public ResponseEntity<Object> handleException(Exception exception) {
		ExceptionPayload exceptionPayload = ExceptionPayload.builder()
			.timestamp(LocalDateTime.now())
			.title("A problem occurred while authenticating")
			.statusCode(HttpStatus.CONFLICT.value())
			.description(exception.getMessage())
			.build();
		
		return new ResponseEntity<>(exceptionPayload, HttpStatus.UNAUTHORIZED);
	}
	
	
}

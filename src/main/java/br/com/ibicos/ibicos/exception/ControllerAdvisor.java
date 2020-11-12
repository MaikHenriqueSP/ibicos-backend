package br.com.ibicos.ibicos.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler  {
		
	@ExceptionHandler(value = {UserAlreadyExistsException.class})
	public ResponseEntity<Object> handleException(UserAlreadyExistsException e) {
		ExceptionPayload exceptionPayload = ExceptionPayload.builder()
			.timestamp(LocalDateTime.now())
			.title("User already registered")
			.statusCode(HttpStatus.CONFLICT.value())
			.description(e.getMessage())
			.build();
		
		return new ResponseEntity<>(exceptionPayload, HttpStatus.CONFLICT);
	}
}

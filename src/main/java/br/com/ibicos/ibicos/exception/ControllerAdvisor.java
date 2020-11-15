package br.com.ibicos.ibicos.exception;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler  {
		
	@ExceptionHandler(value = {UserAlreadyExistsException.class})
	protected ResponseEntity<Object> handleUserAlreadyExistsExceptio(UserAlreadyExistsException exception) {
		Map<String, String> errorsMap = Map.of("email", exception.getMessage());
		ExceptionPayload exceptionPayload = ExceptionPayload.builder()
			.timestamp(LocalDateTime.now())
			.title("An error occurred while signing up")
			.statusCode(HttpStatus.CONFLICT.value())
			.description("User already registered")
			.fieldToMessageMap(errorsMap)
			.build();
		
		return new ResponseEntity<>(exceptionPayload, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = {UsernameNotFoundException.class, BadCredentialsException.class})
	protected ResponseEntity<Object> handleException(Exception exception) {
		ExceptionPayload exceptionPayload = ExceptionPayload.builder()
			.timestamp(LocalDateTime.now())
			.title("A problem occurred while authenticating")
			.statusCode(HttpStatus.CONFLICT.value())
			.description(exception.getMessage())
			.build();
		
		return new ResponseEntity<>(exceptionPayload, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = {InvalidInsertionObjectException.class})
	protected ResponseEntity<Object> handleInvalidInsertionObjectException(Exception exception) {
		ExceptionPayload exceptionPayload = ExceptionPayload.builder()
			.timestamp(LocalDateTime.now())
			.title("Invalid object structure")
			.statusCode(HttpStatus.CONFLICT.value())
			.description(exception.getMessage())
			.build();
		
		return new ResponseEntity<>(exceptionPayload, HttpStatus.CONFLICT);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
		
	    BindingResult bindingResult = ex.getBindingResult();
	    
	    Map<String, String> errorsMap = bindingResult
		    	.getFieldErrors()
		    	.stream()
		    		.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		
		ExceptionPayload exceptionPayload = ExceptionPayload.builder()
				.timestamp(LocalDateTime.now())
				.title("Invalid object field value")
				.statusCode(HttpStatus.CONFLICT.value())
				.description("Total amount of errors: " + bindingResult.getErrorCount()) 
				.fieldToMessageMap(errorsMap)
				.build();

		return new ResponseEntity<>(exceptionPayload, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value= {JpaSystemException.class})
	protected ResponseEntity<Object> handleIllegalArgumentException(JpaSystemException ex) {
		ExceptionPayload exceptionPayload = ExceptionPayload.builder()
				.timestamp(LocalDateTime.now())
				.title("Invalid object field value")
				.statusCode(HttpStatus.CONFLICT.value())
				.description(ex.getMostSpecificCause().getMessage()) 
				.build();
		return new ResponseEntity<>(exceptionPayload, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value= {DataIntegrityViolationException.class})
	protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		
		ExceptionPayload exceptionPayload = ExceptionPayload.builder()
				.timestamp(LocalDateTime.now())
				.title("Invalid object field value")
				.statusCode(HttpStatus.CONFLICT.value())
				.description(ex.getMostSpecificCause().getMessage()) 
				.build();
		return new ResponseEntity<>(exceptionPayload, HttpStatus.CONFLICT);		
	}

	@ExceptionHandler(value= {DisabledException.class})
	protected ResponseEntity<Object> handleDisabledException(DisabledException ex) {
		
		ExceptionPayload exceptionPayload = ExceptionPayload.builder()
				.timestamp(LocalDateTime.now())
				.title("Account not confirmed")
				.statusCode(HttpStatus.CONFLICT.value())
				.description(ex.getMessage()) 
				.build();
		return new ResponseEntity<>(exceptionPayload, HttpStatus.UNAUTHORIZED);		
	}
	
	@ExceptionHandler(value= {EmailSendingException.class})
	protected ResponseEntity<Object> handleEmailSendingException(EmailSendingException ex) {
		
		ExceptionPayload exceptionPayload = ExceptionPayload.builder()
				.timestamp(LocalDateTime.now())
				.title("Error while sending email")
				.statusCode(HttpStatus.CONFLICT.value())
				.description(ex.getMessage()) 
				.build();
		
		return new ResponseEntity<>(exceptionPayload, HttpStatus.UNAUTHORIZED);		
	}
	
	@ExceptionHandler(value = {InvalidTokenException.class})
	protected ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex) {
		ExceptionPayload exceptionPayload = ExceptionPayload.builder()
				.timestamp(LocalDateTime.now())
				.title("Invalid token")
				.statusCode(HttpStatus.CONFLICT.value())
				.description(ex.getMessage()) 
				.build();
		
		return new ResponseEntity<>(exceptionPayload, HttpStatus.UNAUTHORIZED);
	}
}

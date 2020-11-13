package br.com.ibicos.ibicos.exception;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.BadCredentialsException;
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
	
	@ExceptionHandler(value = {InvalidInsertionObjectException.class})
	public ResponseEntity<Object> handleInvalidInsertionObjectException(Exception exception) {
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

}

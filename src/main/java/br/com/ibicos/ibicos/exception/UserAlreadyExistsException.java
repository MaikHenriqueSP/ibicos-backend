package br.com.ibicos.ibicos.exception;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(String message) {
		super(String.format("The email '%s' is already signed up", message));
	}

}

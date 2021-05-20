package br.com.ibicos.ibicos.domain.exception;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(String email) {
		super(String.format("The email '%s' is already in use", email));
	}

}

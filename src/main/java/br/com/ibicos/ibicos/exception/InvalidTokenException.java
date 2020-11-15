package br.com.ibicos.ibicos.exception;

public class InvalidTokenException extends RuntimeException {

	private static final long serialVersionUID = -8661231586962696941L;

	public InvalidTokenException(String message) {
		super(message);
	}

}

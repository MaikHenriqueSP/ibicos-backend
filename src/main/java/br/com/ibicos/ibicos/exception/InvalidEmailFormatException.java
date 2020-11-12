package br.com.ibicos.ibicos.exception;

public class InvalidEmailFormatException extends RuntimeException {

	private static final long serialVersionUID = -4740944348640858005L;
	public InvalidEmailFormatException(String email) {
		super(String.format("The format of the given email '%s' is invalid", email));
	}


}

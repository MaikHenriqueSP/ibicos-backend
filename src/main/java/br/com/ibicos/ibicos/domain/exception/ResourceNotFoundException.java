package br.com.ibicos.ibicos.domain.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4870885287350391097L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	

}

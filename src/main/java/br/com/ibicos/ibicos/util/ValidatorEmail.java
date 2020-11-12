package br.com.ibicos.ibicos.util;

import org.apache.commons.validator.routines.EmailValidator;

public class ValidatorEmail {
	
	public static boolean isEmailValid(String email) {
		EmailValidator emailValidator = EmailValidator.getInstance();
		return emailValidator.isValid(email);
	}

}

package br.com.ibicos.ibicos.service;

import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.exception.UserAlreadyExistsException;

public interface IUserService {

	User save(User user) throws UserAlreadyExistsException;

	void verifyAccountRequestHandler(String verificationToken);

	void resetPasswordRequestHandler(String email);

	void changeUserPassword(String accountRecoveryToken, String newPassword);
	
	User findUserById(Integer idUser);
	
	User updateUser(User user);
	
	Boolean isEmailInUse(String email);
}

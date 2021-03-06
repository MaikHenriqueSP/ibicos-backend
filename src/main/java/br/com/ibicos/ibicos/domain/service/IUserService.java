package br.com.ibicos.ibicos.domain.service;

import br.com.ibicos.ibicos.domain.entity.User;
import br.com.ibicos.ibicos.domain.exception.UserAlreadyExistsException;

public interface IUserService {

	User save(User user) throws UserAlreadyExistsException;

	void verifyAccountRequestHandler(String verificationToken);

	void resetPasswordRequestHandler(String email);

	void changeUserPassword(String accountRecoveryToken, String newPassword);
	
	User findUserById(Integer idUser);
	
	User updateUser(User user);
	
	Boolean isEmailInUse(String email);
	
	User findUserByEmail(String email);
}

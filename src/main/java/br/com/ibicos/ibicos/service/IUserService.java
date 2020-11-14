package br.com.ibicos.ibicos.service;

import java.util.Optional;

import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.exception.UserAlreadyExistsException;

public interface IUserService {

	User save(User user) throws UserAlreadyExistsException;
	Optional<User> findUserByEmail(String email);
	User verifyAccount(String verificationToken);
}

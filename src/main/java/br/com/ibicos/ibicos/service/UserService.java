package br.com.ibicos.ibicos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.exception.InvalidEmailFormatException;
import br.com.ibicos.ibicos.exception.InvalidInsertionObjectException;
import br.com.ibicos.ibicos.exception.UserAlreadyExistsException;
import br.com.ibicos.ibicos.repository.UserRepository;
import br.com.ibicos.ibicos.util.ValidatorEmail;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional(rollbackFor = {DataIntegrityViolationException.class,
			InvalidEmailFormatException.class})	
	public User save(User user)  {	
		String userEmail = user.getEmail();
		
		if(!ValidatorEmail.isEmailValid(userEmail)) {
			throw new InvalidEmailFormatException(userEmail);
		}	
				
		boolean isUserPresent = getUserByEmail(userEmail).isPresent();
		
		try {
			return userRepository.save(user);		
		} catch(DataIntegrityViolationException e) {		
			if(isUserPresent) {
				throw new UserAlreadyExistsException(userEmail);
			}
			throw new InvalidInsertionObjectException("The received object is in an invalid format"
					+ ", please check the documentation for the correct one");
		} 
		
	}
	
	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}

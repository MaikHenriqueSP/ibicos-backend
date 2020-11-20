package br.com.ibicos.ibicos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ibicos.ibicos.email.EmailService;
import br.com.ibicos.ibicos.entity.Address;
import br.com.ibicos.ibicos.entity.Person;
import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.exception.EmailSendingException;
import br.com.ibicos.ibicos.exception.InvalidInsertionObjectException;
import br.com.ibicos.ibicos.exception.InvalidTokenException;
import br.com.ibicos.ibicos.exception.ResourceNotFoundException;
import br.com.ibicos.ibicos.exception.UserAlreadyExistsException;
import br.com.ibicos.ibicos.repository.UserRepository;
import net.bytebuddy.utility.RandomString;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailService;

	private void encodeUserPassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPasswordUser());
		user.setPasswordUser(encodedPassword);
	}

	@Transactional(rollbackFor = { DataIntegrityViolationException.class, EmailSendingException.class })
	public User save(User user) {
		encodeUserPassword(user);
		String userEmail = user.getEmail();
		boolean isUserPresent = userRepository.findByEmail(userEmail).isPresent();

		try {
			User savedUser = userRepository.save(user);
			String validationToken = savedUser.getValidationToken();

			emailService.sendValidationToken(savedUser.getPerson().getNamePerson(), validationToken,
					savedUser.getEmail());

			return savedUser;
		} catch (DataIntegrityViolationException e) {
			if (isUserPresent) {
				throw new UserAlreadyExistsException(userEmail);
			}
			throw new InvalidInsertionObjectException("The received object is in an invalid format"
					+ ", please check the documentation for the correct one");
		}
	}

	@Override
	public void verifyAccountRequestHandler(String validationToken) {
		Optional<User> optUser = userRepository.findUserByValidationToken(validationToken);

		if (!optUser.isPresent()) {
			throw new InvalidTokenException("Invalid verification token, please contact the support team");
		}

		User user = optUser.get();
		user.setIsAccountConfirmed(true);

		updateUser(user);
	}

	@Override
	public void resetPasswordRequestHandler(String email) {
		Optional<User> userOptional = userRepository.findByEmail(email);

		if (userOptional.isEmpty()) {
			throw new UsernameNotFoundException("The user with '" + email + "' was not found");
		}

		User user = userOptional.get();

		if (!user.getIsAccountConfirmed()) {
			throw new DisabledException("Account is not yet confirmed,"
					+ " please validate it firt through your email, before trying to change it's password");
		}

		emailService.sendRecoveryEmail(user.getPerson().getNamePerson(), user.getEmail(),
				user.getAccountRecoveryToken());
	}


	@Override
	public void changeUserPassword(String accountRecoveryToken, String newPassword) {
		Optional<User> optionalUser = userRepository.findByAccountRecoveryToken(accountRecoveryToken);

		if (optionalUser.isEmpty()) {
			throw new InvalidTokenException("Invalid token, please contact the support team");
		}

		User user = optionalUser.get();
		user.setPasswordUser(newPassword);
		encodeUserPassword(user);

		user.setAccountRecoveryToken(RandomString.make(64));
		userRepository.save(user);
	}

	@Override
	public User findUserById(Integer idUser) {
		Optional<User> optionalUser = userRepository.findById(idUser);
		
		if (optionalUser.isEmpty()) {
			throw new ResourceNotFoundException("We did not find any user with the provided ID");
		}		
		return optionalUser.get();		
	}
	
	@Override
	public User updateUser(User user) {			
		Optional<User> optionalUser = userRepository.findById(user.getId());
		
		if (optionalUser.isEmpty()) {
			throw new ResourceNotFoundException("We did not find any user with the provided ID");
		}
		
		User oldUser = optionalUser.get();
		oldUser.setNotice(user.getNotice());
		
		Person newPerson = user.getPerson();
		Person oldPerson = oldUser.getPerson();
		
		if (newPerson.getCnpj() != null) {
			oldPerson.setCnpj(newPerson.getCnpj());
		}
		
		Address oldAddress = oldPerson.getAddress();
		Address newAddress = newPerson.getAddress();
		
		oldAddress.setCep(newAddress.getCep());
		oldAddress.setCity(newAddress.getCity());
		
		if (newAddress.getComplement() != null) { 
			oldAddress.setComplement(newAddress.getComplement());
		}
		
		oldAddress.setNeighborhood(newAddress.getNeighborhood());
		oldAddress.setNumberAddress(newAddress.getNumberAddress());
		
		oldAddress.setState(newAddress.getState());
		oldAddress.setStreet(newAddress.getStreet());
		
		return userRepository.save(oldUser);
	}
	
	

}

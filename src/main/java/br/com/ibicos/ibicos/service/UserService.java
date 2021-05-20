package br.com.ibicos.ibicos.service;

import br.com.ibicos.ibicos.domain.entity.Person;
import br.com.ibicos.ibicos.domain.entity.User;
import br.com.ibicos.ibicos.event.PasswordResetRequestEvent;
import br.com.ibicos.ibicos.exception.*;
import br.com.ibicos.ibicos.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements IUserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final StatisticsService statisticsService;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final PersonService personService;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, StatisticsService statisticsService,
					   ApplicationEventPublisher applicationEventPublisher, PersonService personService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.statisticsService = statisticsService;
		this.applicationEventPublisher = applicationEventPublisher;
		this.personService = personService;
	}

	private void encodeUserPassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPasswordUser());
		user.setPasswordUser(encodedPassword);
	}

	@Transactional(rollbackFor = { DataIntegrityViolationException.class, EmailSendingException.class })
	public User save(User user) {
		encodeUserPassword(user);
		String userEmail = user.getEmail();

		if (userRepository.findByEmail(userEmail).isPresent()) {
			throw new UserAlreadyExistsException(userEmail);
		}

		return saveAndReturnNewUser(user);
	}

	private User saveAndReturnNewUser(User user) {
		try {
			User savedUser = userRepository.save(user);
			applicationEventPublisher.publishEvent(user);
			return savedUser;
		} catch (DataIntegrityViolationException e) {
			throw new InvalidInsertionObjectException("The received object is in an invalid format"
					+ ", please check the documentation for the correct one");
		}
	}


	@Override
	public void verifyAccountRequestHandler(String validationToken) {
		Optional<User> optUser = userRepository.findUserByValidationToken(validationToken);

		if (optUser.isEmpty()) {
			throw new InvalidTokenException("Invalid verification token, please contact the support team");
		}

		User user = optUser.get();
		user.setIsAccountConfirmed(true);
		statisticsService.createCustomerStatistics(user);

		userRepository.save(user);
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
					+ " please validate it first through your email, before trying to change it's password");
		}

		applicationEventPublisher.publishEvent(new PasswordResetRequestEvent(user));

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

		personService.updatePerson(oldPerson, newPerson);

		return userRepository.save(oldUser);
	}

	@Override
	public Boolean isEmailInUse(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public User findUserByEmail(String email) {
		Optional<User> userOptional =  userRepository.findByEmail(email);
		if (userOptional.isEmpty()) { 
			throw new ResourceNotFoundException("There's no user with the given email");
		}
		return userOptional.get();
	}

}

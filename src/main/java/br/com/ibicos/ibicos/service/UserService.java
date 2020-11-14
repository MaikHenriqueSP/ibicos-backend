package br.com.ibicos.ibicos.service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.exception.InvalidInsertionObjectException;
import br.com.ibicos.ibicos.exception.UserAlreadyExistsException;
import br.com.ibicos.ibicos.repository.UserRepository;


@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	private void sendValidationEmail(String validationToken)  {
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("ibicos.dont-reply@gmail.com", "iBicos Classificados");
			helper.setTo("maik.hsp.contato@gmail.com");
						
			String mailSubject = "Confirmação de cadastro";
			
			String mailContent = "<p>Olá, tudo bem?</p>"  
							+    "<p></p>"
							+	 "<p>Caso não tenha feito o cadastro na plataforma iBicos apenas ignore o email</p>"
							+ 	 "<a href='http://localhost:8080/verify?token="+ validationToken + "'>Clique para validar conta</a>";
			helper.setSubject(mailSubject);			
			
			helper.setText(mailContent + "<hr><img src='cid:myLogo'>", true);
			helper.addInline("myLogo", new ClassPathResource("/static/images/logoiBicos.png"));

		} catch (UnsupportedEncodingException | MessagingException e) {

			e.printStackTrace();
		}
		
		mailSender.send(message);
	}	
	
	
	
	@Transactional(rollbackFor = {DataIntegrityViolationException.class})	
	public User save(User user)  {	
		String userEmail = user.getEmail();
						
		boolean isUserPresent = findUserByEmail(userEmail).isPresent();
		
		try {
			User savedUser =  userRepository.save(user);					
			String validationToken =	savedUser.getValidationToken();
			sendValidationEmail(validationToken);			
			return savedUser;
		} catch(DataIntegrityViolationException e) {		
			if(isUserPresent) {
				throw new UserAlreadyExistsException(userEmail);
			}
			throw new InvalidInsertionObjectException("The received object is in an invalid format"
					+ ", please check the documentation for the correct one");
		} 		
	}	
	
	public Optional<User> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public User verifyAccount(String validationToken) {
		Optional<User> optUser = userRepository.findUserByValidationToken(validationToken);
		if (optUser.isPresent()) {
			User user = optUser.get();
			user.setIsAccountConfirmed(true);
			return updateUser(user);			
		}
		return null;
	}	
	
	public User updateUser(User user) {
		userRepository.save(user);
		return user;
	}


}

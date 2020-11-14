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
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.exception.InvalidInsertionObjectException;
import br.com.ibicos.ibicos.exception.UserAlreadyExistsException;
import br.com.ibicos.ibicos.repository.UserRepository;
import net.bytebuddy.utility.RandomString;


@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/* EMAIL*/
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
	
	private void encodeUserPassword(User user) {		
		String encodedPassword = passwordEncoder.encode(user.getPasswordUser());
		user.setPasswordUser(encodedPassword);
	}
	
	@Transactional(rollbackFor = {DataIntegrityViolationException.class})	
	public User save(User user)  {	
		encodeUserPassword(user);
		String userEmail = user.getEmail();						
		boolean isUserPresent = userRepository.findByEmail(userEmail).isPresent();
		
		try {
			User savedUser =  userRepository.save(user);					
			String validationToken = savedUser.getValidationToken();
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
		
	@Override
	public User verifyAccount(String validationToken) {
		Optional<User> optUser = userRepository.findUserByValidationToken(validationToken);
		
		if (!optUser.isPresent()) {
			throw new InvalidInsertionObjectException("a");
		}		
		
		User user = optUser.get();
		user.setIsAccountConfirmed(true);
		return updateUser(user);			
	}	
	
	public User updateUser(User user) {
		userRepository.save(user);
		return user;
	}
	
	@Override
	public Boolean resetPasswordProcessing(String email) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		
		if (userOptional.isEmpty()) {
			throw new UserAlreadyExistsException("NÃO TEM USUÁRIO CADASTRADO COM: " + email);
		}
		
		User user = userOptional.get();
		
		if(!user.getIsAccountConfirmed()) {
			throw new DisabledException("Account is not yet confirmed,"
					+ " please validate it firt through your email, before trying to change it's email");
		}
		
		sendRecoveryEmail(userOptional.get().getAccountRecoveryToken());
		return true;
	}
	
	/* EMAIL*/
	private void sendRecoveryEmail(String accountRecoveryToken)  {
		MimeMessage message = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("ibicos.dont-reply@gmail.com", "iBicos Classificados");
			helper.setTo("maik.hsp.contato@gmail.com");
						
			String mailSubject = "Recuperação de senha";
			
			String mailContent = "<p>Olá, tudo bem?</p>"  
							+    "<p></p>"
							+	 "<p>Recebemos uma requisição de alteração de senha, caso não tenha feito apenas ignore o email</p>"
							+ 	 "<a href='http://localhost:8080/recoverPassword?token="+ accountRecoveryToken + "'>Clique atualizar sua senha</a>";
			helper.setSubject(mailSubject);			
			
			helper.setText(mailContent + "<hr><img src='cid:myLogo'>", true);
			helper.addInline("myLogo", new ClassPathResource("/static/images/logoiBicos.png"));

		} catch (UnsupportedEncodingException | MessagingException e) {

			e.printStackTrace();
		}
		
		mailSender.send(message);
	}	
	
	
	public Boolean isAccountRecoveryTokenValid(String token) {		
		return userRepository.findByAccountRecoveryToken(token).isPresent();		
	}
	
	@Override
	public void changeUserPassword(String accountRecoveryToken, String newPassword) {
		Optional<User> optionalUser = userRepository.findByAccountRecoveryToken(accountRecoveryToken);
		
		if(optionalUser.isEmpty()) {
			throw new UserAlreadyExistsException("Invalid token.");
		}
		
		User user = optionalUser.get();
		user.setPasswordUser(newPassword);
		encodeUserPassword(user);
		
		user.setAccountRecoveryToken(RandomString.make(64));		
		userRepository.save(user);		
	}


}

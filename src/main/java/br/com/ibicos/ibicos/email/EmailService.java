package br.com.ibicos.ibicos.email;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import br.com.ibicos.ibicos.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import br.com.ibicos.ibicos.dto.CustomerEmailToProviderDTO;
import br.com.ibicos.ibicos.dto.EmailTokenConfigDTO;
import br.com.ibicos.ibicos.exception.EmailSendingException;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SpringTemplateEngine springTemplateEngine;

	public void sendEmailTokenTemplate(EmailTokenConfigDTO emailTokenConfigDTO) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			Context context = new Context();
			context.setVariable("nome", emailTokenConfigDTO.getReceiverName());
			context.setVariable("token", emailTokenConfigDTO.getToken());

			String html = springTemplateEngine.process(emailTokenConfigDTO.getHtmlTemplateName(), context);

			mimeMessageHelper.setTo(emailTokenConfigDTO.getToEmail());

			mimeMessageHelper.setText(html, true);
			mimeMessageHelper.setSubject(emailTokenConfigDTO.getSubject());
			mimeMessageHelper.setFrom(emailTokenConfigDTO.getFromEmail(), emailTokenConfigDTO.getFromTitle());
		} catch (MessagingException | UnsupportedEncodingException e) {
			throw new EmailSendingException("An error occurred while sending email, please try again");
		}

		javaMailSender.send(mimeMessage);
	}

	public void sendValidationToken(String name, String validationToken, String email) {
		EmailTokenConfigDTO emailTokenConfigDTO = EmailTokenConfigDTO.builder().receiverName(name)
				.toEmail(email)
				.token(validationToken)
				.fromEmail("ibicos.classificados@gmail.com")
				.fromTitle("iBicos - Suporte")
				.htmlTemplateName("email-validation")
				.receiverName(name)
				.subject("iBicos - Confirmação de cadastro")
				.build();
		
		sendEmailTokenTemplate(emailTokenConfigDTO);
	}
	
	public void sendRecoveryEmail(String name, String email, String accountRecoveryToken) {
		EmailTokenConfigDTO emailTokenConfigDTO = EmailTokenConfigDTO.builder().receiverName(name)
				.toEmail(email)
				.token(accountRecoveryToken)
				.fromEmail("ibicos.classificados@gmail.com")
				.fromTitle("iBicos - Suporte")
				.htmlTemplateName("email-recover")
				.receiverName(name)
				.subject("iBicos - Redefinição de senha")
				.build();
		
		sendEmailTokenTemplate(emailTokenConfigDTO);
	}

	@Async
	public void sendEmailToProvider(User provider, Map<String, Object> contextEmailMapVariables) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;

		String providerEmailAddress = provider.getEmail();

		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			Context context = getContext(contextEmailMapVariables);
			String html = springTemplateEngine.process("email-message-from-customer-to-provider", context);
			mimeMessageHelper.setTo(providerEmailAddress);
			mimeMessageHelper.setText(html, true);
			mimeMessageHelper.setSubject("iBicos - Mensagem do cliente");
			mimeMessageHelper.setFrom("ibicos.classificados@gmail.com", "iBicos - Suporte");
		} catch (MessagingException | UnsupportedEncodingException e) {
			throw new EmailSendingException("An error occurred while sending email, please try again");
		}

		javaMailSender.send(mimeMessage);
	}

	private Context getContext(Map<String, Object> contextMapVariables) {
		Context context = new Context();
		context.setVariables(contextMapVariables);
		return context;
	}




}

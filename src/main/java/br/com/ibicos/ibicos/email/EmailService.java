package br.com.ibicos.ibicos.email;

import br.com.ibicos.ibicos.dto.EmailDataDTO;
import br.com.ibicos.ibicos.domain.exception.EmailSendingException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailService {

	private final JavaMailSender javaMailSender;
	private final SpringTemplateEngine springTemplateEngine;

	public EmailService(JavaMailSender javaMailSender, SpringTemplateEngine springTemplateEngine) {
		this.javaMailSender = javaMailSender;
		this.springTemplateEngine = springTemplateEngine;
	}

	@Async
	public void sendEmail(EmailDataDTO emailData, Map<String, Object> contextEmailMapVariables) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		configureMimeMessage(emailData, contextEmailMapVariables, mimeMessage);
		javaMailSender.send(mimeMessage);
	}

	private void configureMimeMessage(EmailDataDTO emailData, Map<String, Object> contextEmailMapVariables, MimeMessage mimeMessage) {
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			Context context = getContext(contextEmailMapVariables);
			String html = springTemplateEngine.process(emailData.getTemplateName(), context);
			configureMimeMessageHelper(mimeMessageHelper, html, emailData);
		} catch (MessagingException | UnsupportedEncodingException e) {
			throw new EmailSendingException("An error occurred while sending email, please try again");
		}
	}

	private void configureMimeMessageHelper(MimeMessageHelper mimeMessageHelper, String html, EmailDataDTO emailData)
			throws MessagingException, UnsupportedEncodingException {
		mimeMessageHelper.setTo(emailData.getTo());
		mimeMessageHelper.setText(html, true);
		mimeMessageHelper.setSubject(emailData.getSubject());
		mimeMessageHelper.setFrom(emailData.getFrom(), emailData.getSubject());
	}

	private Context getContext(Map<String, Object> contextMapVariables) {
		Context context = new Context();
		context.setVariables(contextMapVariables);
		return context;
	}

}

package br.com.ibicos.ibicos.service;

import br.com.ibicos.ibicos.dto.EmailDataDTO;
import br.com.ibicos.ibicos.email.EmailService;
import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.event.PasswordResetRequestEvent;
import br.com.ibicos.ibicos.event.SignedUpUserEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailObserver {
    private final EmailService emailService;

    public EmailObserver(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void signedUpUserEventHandler(SignedUpUserEvent signedUpUserEvent) {
        User user = signedUpUserEvent.getUser();
        String validationToken = user.getValidationToken();

        EmailDataDTO emailData = EmailDataDTO.builder()
                .to(user.getEmail())
                .from("ibicos.classificados@gmail.com")
                .subject("iBicos - Confirmação de cadastro")
                .templateName("email-validation")
                .build();

        Map<String, Object> mapContext = getMapContext(user, validationToken);

        emailService.sendEmail(emailData, mapContext);
    }

    @EventListener
    public void passwordRequestEventHandler(PasswordResetRequestEvent passwordResetRequestEvent) {
        User user = passwordResetRequestEvent.getUser();

        String recoveryToken = user.getAccountRecoveryToken();

        EmailDataDTO emailData = EmailDataDTO.builder()
				.subject("iBicos - Redefinição de senha")
				.to(user.getEmail())
				.from("ibicos.classificados@gmail.com")
				.templateName("email-recover")
				.build();

        Map<String, Object> mapContext = getMapContext(user, recoveryToken);

        emailService.sendEmail(emailData, mapContext);
    }

    private Map<String, Object> getMapContext(User user, String token) {
        return Map.of("nome", user.getPerson().getNamePerson(), "token",
                token);
    }
}

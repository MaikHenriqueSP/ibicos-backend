package br.com.ibicos.ibicos.domain.listener;

import br.com.ibicos.ibicos.domain.entity.User;
import br.com.ibicos.ibicos.domain.event.PasswordResetRequestEvent;
import br.com.ibicos.ibicos.dto.EmailDataDTO;
import br.com.ibicos.ibicos.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@AllArgsConstructor
@Component
public class PasswordResetRequestListener {

    private final EmailService emailService;

    @EventListener
    public void onPasswordRequest(PasswordResetRequestEvent passwordResetRequestEvent) {
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

package br.com.ibicos.ibicos.domain.listener;

import br.com.ibicos.ibicos.domain.entity.User;
import br.com.ibicos.ibicos.domain.event.SignedUpUserEvent;
import br.com.ibicos.ibicos.api.dto.EmailDataDTO;
import br.com.ibicos.ibicos.domain.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class SignedUpUserListener {
    private final EmailService emailService;

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

    private Map<String, Object> getMapContext(User user, String token) {
        return Map.of("nome", user.getPerson().getNamePerson(), "token",
                token);
    }
}

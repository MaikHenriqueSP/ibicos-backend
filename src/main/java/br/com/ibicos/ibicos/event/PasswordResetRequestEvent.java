package br.com.ibicos.ibicos.event;

import br.com.ibicos.ibicos.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PasswordResetRequestEvent {
    private final User user;
}

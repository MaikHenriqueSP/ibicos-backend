package br.com.ibicos.ibicos.domain.event;

import br.com.ibicos.ibicos.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignedUpUserEvent {
    private final User user;
}

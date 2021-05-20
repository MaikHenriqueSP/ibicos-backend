package br.com.ibicos.ibicos.mapper;

import br.com.ibicos.ibicos.api.dto.UserDTO;
import br.com.ibicos.ibicos.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO userToUserDTO(User user);
}

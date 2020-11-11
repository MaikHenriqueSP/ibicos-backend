package br.com.ibicos.ibicos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredentialsDTO {
	private String email;
	private String passwordUser;

}

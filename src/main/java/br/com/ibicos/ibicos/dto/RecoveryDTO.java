package br.com.ibicos.ibicos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecoveryDTO {
	private String accountRecoveryToken;
	private String newPassword;
	
}

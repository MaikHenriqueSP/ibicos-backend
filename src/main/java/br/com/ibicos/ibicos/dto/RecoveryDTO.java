package br.com.ibicos.ibicos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecoveryDTO {
	private String accountRecoveryToken;
	private String newPassword;
	
}

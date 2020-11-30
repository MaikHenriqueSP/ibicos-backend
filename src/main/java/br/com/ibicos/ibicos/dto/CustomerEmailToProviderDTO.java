package br.com.ibicos.ibicos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEmailToProviderDTO {
	private String providerEmailAddress;
	private String customerEmailAddress;
	private String customerName;
	private String providerName;
	private String message;

}

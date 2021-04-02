package br.com.ibicos.ibicos.dto;

import br.com.ibicos.ibicos.entity.ServiceCategory;
import br.com.ibicos.ibicos.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEmailToProviderDTO {
	private User provider;
	private User customer;
	private ServiceCategory serviceCategory;
	private String message;

}

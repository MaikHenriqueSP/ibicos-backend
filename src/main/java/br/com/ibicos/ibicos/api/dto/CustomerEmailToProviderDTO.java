package br.com.ibicos.ibicos.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEmailToProviderDTO {
	private Integer providerId;
	private Integer customerId;
	@JsonProperty("serviceCategory")
	private ServiceCategoryDTO serviceCategoryDTO;
	private String message;

}

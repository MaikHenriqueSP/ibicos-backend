package br.com.ibicos.ibicos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EvaluationDTO {
	private Integer idEvaluate;
	private Float evaluation;
	private Integer idClient;
	private Integer idProvider;
	private Integer idCategory;
 
}

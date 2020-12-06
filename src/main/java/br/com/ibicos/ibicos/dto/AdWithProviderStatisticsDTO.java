package br.com.ibicos.ibicos.dto;

import br.com.ibicos.ibicos.entity.Ad;
import br.com.ibicos.ibicos.entity.ProviderStatistics;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface AdWithProviderStatisticsDTO {
	String getAdDescription();
	String getCategoryName();
	Integer getEvaluationsCounter();
	Float getEvaluation(); 
	Integer getVisualizations();
	Integer getIdUser();	
}

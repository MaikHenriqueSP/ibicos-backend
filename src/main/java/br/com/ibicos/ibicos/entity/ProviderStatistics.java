package br.com.ibicos.ibicos.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderStatistics {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idProviderStatistics;
	private Integer visualizations;
	
	@OneToOne
	@JoinColumn(name = "fk_id_statistics")
	private Statistics statistics;
	@OneToOne
	@JoinColumn(name= "fk_id_service_cat")
	private Category category;
}

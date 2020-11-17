package br.com.ibicos.ibicos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProviderStatistics {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_provider_statistics")
	private Integer id;
	private Integer visualizations;
	
	@OneToOne
	@JoinColumn(name = "fk_id_statistics")
	private Statistics statistics;
	
	@OneToOne
	@JoinColumn(name= "fk_id_service_category")
	private ServiceCategory category;
}

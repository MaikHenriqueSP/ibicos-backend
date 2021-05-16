package br.com.ibicos.ibicos.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderStatistics  extends Statistics{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_provider_statistics")
	private Integer id;
	private Integer visualizations;

	@OneToOne
	@JoinColumn(name= "fk_id_service_category")
	private ServiceCategory category;

}

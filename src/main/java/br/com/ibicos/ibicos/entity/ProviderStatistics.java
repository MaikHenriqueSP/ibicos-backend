package br.com.ibicos.ibicos.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
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

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
@PrimaryKeyJoinColumn(name="id_provider_statistics")
public class ProviderStatistics  extends Statistics{

	private Integer visualizations;

	@OneToOne
	@JoinColumn(name= "fk_id_service_category")
	private ServiceCategory category;


}

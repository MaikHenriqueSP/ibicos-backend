package br.com.ibicos.ibicos.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@PrimaryKeyJoinColumn(name="id_provider_statistics")
public class ProviderStatistics  extends Statistics{

	private Integer visualizations;

	@ManyToOne
	@JoinColumn(name= "fk_id_service_category")
	private ServiceCategory category;


}

package br.com.ibicos.ibicos.entity;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderStatistics {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_provider_statistics")
	private Integer id;
	private Integer visualizations;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_id_statistics")
	private Statistics statistics;

	@OneToOne
	@JoinColumn(name= "fk_id_service_category")
	private ServiceCategory category;
	
//	@JsonIgnore
//	@OneToMany(mappedBy = "providerStatistics")
//	private List<Ad> ad;
}

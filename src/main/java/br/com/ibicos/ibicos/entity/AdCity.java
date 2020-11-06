package br.com.ibicos.ibicos.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdCity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer idCity;
	private String cityName;
	private String stateAbb;
	@ManyToOne
	@JoinColumn(name="fk_id_ad")
	private Ad ad;

	@OneToMany
	@JoinColumn(name="fk_id_area")
	private List<AdRegionArea> regionArea;
}

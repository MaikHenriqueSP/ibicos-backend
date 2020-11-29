package br.com.ibicos.ibicos.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class AdCity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer idCity;
	
	@NotBlank
	private String cityName;
	
	@NotBlank
	private String stateAbb;
	
	@ManyToOne()
	@JoinColumn(name="fk_id_ad")
	@JsonIgnoreProperties(value = "cities")
	private Ad ad;

	
	@NotNull
	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "adCity")
	@JsonIgnoreProperties(value = "adCity")
	private List<@Valid AdRegionArea> regionArea;
}

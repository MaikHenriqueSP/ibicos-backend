package br.com.ibicos.ibicos.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

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
	
	@ManyToOne
	@JoinColumn(name="fk_id_ad")
	@JsonIgnoreProperties(value = "cities")
	private Ad ad;

	
	@NotNull
	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "adCity", fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = "adCity")
	private List<@Valid AdRegionArea> regionArea;
	
	@PrePersist	
	private void prePersist() {
	    if(regionArea != null && regionArea.size() > 0) {
	    	for(AdRegionArea region: regionArea) {
	    		region.setAdCity(this);
	    	}
	    };
	}
}

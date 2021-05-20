package br.com.ibicos.ibicos.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Ad {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Column(name = "id_ad")
	private Integer id;	
	
	@NotBlank
	@Size(min = 10, max = 150)	
	private String adDescription;	
	
	@NotNull	
	@ManyToOne
	@JoinColumn(name="fk_id_user")
	private User user;

	@Valid
	@NotNull
	@ManyToOne
	@JoinColumn(name = "fk_id_service_category")
	private ServiceCategory serviceCategory;

	@NotNull
	@NotEmpty
	@JsonIgnoreProperties(value = "ad")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ad", fetch = FetchType.EAGER)
	private List<@Valid AdCity> cities;

	@ManyToOne
	@JoinColumn(name = "fk_id_provider_statistics")
	private ProviderStatistics providerStatistics;
	
	@PrePersist
	private void prePersist() {
	    if(cities != null && cities.size() > 0) {
	    	for(AdCity city: cities) {
	    		city.setAd(this);
	    	}
	    };
	}

	public String getAdDescription() {
		return adDescription;
	}

	public void setAdDescription(String adDescription) {
		this.adDescription = adDescription;
	}
}

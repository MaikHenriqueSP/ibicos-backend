package br.com.ibicos.ibicos.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	@OneToOne()
	@JoinColumn(name="fk_id_user")
	private User user;

	@Valid
	@NotNull
	@OneToOne()
	@JoinColumn(name = "fk_id_service_category")
	private ServiceCategory serviceCategory;

	@NotNull
	@NotEmpty	
	@JsonIgnoreProperties(value = "ad")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ad")
	private List<@Valid AdCity> cities;
	

	@ManyToOne
	@JsonProperty(access = Access.READ_ONLY)
	@JoinColumn(name="fk_id_service_category", insertable = false, updatable = false)
	private ProviderStatistics providerStatistics;
}

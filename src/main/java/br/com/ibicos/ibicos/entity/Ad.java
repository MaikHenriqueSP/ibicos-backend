package br.com.ibicos.ibicos.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ad {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer idAd;	
	private String adDescription;	
	
	@OneToOne()
	@JoinColumn(name="fk_user")
	private User user;
	
	@OneToOne()
	@JoinColumn(name = "fk_service_cat")
	private ServiceCategory serviceCategory;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ad")
	private List<AdCity> cities;
	
}

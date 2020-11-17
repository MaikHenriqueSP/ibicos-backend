package br.com.ibicos.ibicos.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	private String adDescription;	
	
	@OneToOne()
	@JoinColumn(name="fk_id_user")
	private User user;
	
	@OneToOne()
	@JoinColumn(name = "fk_id_service_category")
	private ServiceCategory serviceCategory;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ad")
	@JsonIgnoreProperties(value = "ad")
	private List<AdCity> cities;
	
}

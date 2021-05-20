package br.com.ibicos.ibicos.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AdRegionArea {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer idArea;
	
	@NotBlank
	private String areaName;
	
	@ManyToOne
	@JoinColumn(name="fk_id_city")
	@JsonIgnoreProperties(value = "regionArea")
	private AdCity adCity;	
	
}

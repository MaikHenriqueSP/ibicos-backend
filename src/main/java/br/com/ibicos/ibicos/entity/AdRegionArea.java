package br.com.ibicos.ibicos.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdRegionArea {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer idArea;
	private String areaName;
	

	@ManyToOne
	@JoinColumn(name="fk_city")
	private AdCity adCity;	
	
}

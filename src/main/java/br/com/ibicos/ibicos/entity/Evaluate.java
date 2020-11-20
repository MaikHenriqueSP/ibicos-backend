package br.com.ibicos.ibicos.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude= {"client", "provider", "serviceCategory"})
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "idEvaluate",
		  scope = Evaluate.class)
public class Evaluate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idEvaluate;
	private LocalDate messageDate;
	private boolean hired;
	private boolean customerEvaluated;
	private boolean providerEvaluated;
	
	@OneToOne
	@ToString.Exclude
	@JoinColumn(name = "fk_id_client")
	private User client;
	
	@OneToOne
	@ToString.Exclude
	@JoinColumn(name = "fk_id_provider")
	private User provider;
	
	@OneToOne
	@ToString.Exclude
	@JoinColumn(name = "fk_id_service_category")
	private ServiceCategory serviceCategory;
	
}

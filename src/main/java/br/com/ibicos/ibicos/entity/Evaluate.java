package br.com.ibicos.ibicos.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude= {"client", "provider", "serviceCategory"})
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "idEvaluate",
		  scope = Evaluate.class)
@Builder
public class Evaluate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idEvaluate;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate messageDate;
	private boolean hired;
	private boolean customerEvaluated;
	private boolean providerEvaluated;
	
	@ManyToOne
	@ToString.Exclude
	@JoinColumn(name = "fk_id_client")
	private User client;
	
	@ManyToOne
	@ToString.Exclude
	@JoinColumn(name = "fk_id_provider")
	private User provider;
	
	@ManyToOne
	@ToString.Exclude
	@JoinColumn(name = "fk_id_service_category")
	private ServiceCategory serviceCategory;
	
}

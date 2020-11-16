package br.com.ibicos.ibicos.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Evaluate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idEvalute;
	private LocalDate messageDate;
	private boolean hired;
	private boolean clienteEvaluated;
	private boolean providerEvaluated;
	
	@OneToOne
	@JoinColumn(name = "fk_id_client")
	private User client;
	@OneToOne
	@JoinColumn(name = "fk_id_provider")
	private User provider;
	
}

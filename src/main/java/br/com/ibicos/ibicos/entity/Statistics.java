package br.com.ibicos.ibicos.entity;

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
public class Statistics {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idStatistics;
	private Integer viewsNumber;
	private Float evaluations;
	private Integer evaluationsNumber;
	private Integer hiredServices;
	private Integer receivedMessages;
	@OneToOne
	@JoinColumn(name="fk_id_user")
	private User user;

	
}

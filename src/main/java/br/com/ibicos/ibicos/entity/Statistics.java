package br.com.ibicos.ibicos.entity;

import javax.persistence.Column;
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
	@Column(name = "id_statistics")
	private Integer id;
	private Float evaluation;
	private Integer viewsCounter;
	private Integer evaluationsCounter;
	private Integer hiredServicesCounter;
	private Integer messagesCounter;
	@OneToOne
	@JoinColumn(name="fk_id_user")
	private User user;

	
}

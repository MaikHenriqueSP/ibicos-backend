package br.com.ibicos.ibicos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = {"user"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class, 
		property = "id",
		scope = Statistics.class)
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
	@ToString.Exclude
	@JsonIgnore
	private User user;

	
}

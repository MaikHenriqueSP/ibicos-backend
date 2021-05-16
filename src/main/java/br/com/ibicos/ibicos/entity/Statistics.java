package br.com.ibicos.ibicos.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.*;

@Data
@EqualsAndHashCode(exclude = {"user"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class, 
		property = "id",
		scope = Statistics.class)
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
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

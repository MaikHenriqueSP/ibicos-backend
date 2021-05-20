package br.com.ibicos.ibicos.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"user"})
@Entity
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class, 
		property = "id",
		scope = Statistics.class)

@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Statistics {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_statistics")
	private Integer id;
	private Float evaluation;
	private Integer evaluationsCounter;
	private Integer hiredServicesCounter;
	private Integer messagesCounter;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fk_id_user")
	@ToString.Exclude
	@JsonIgnore
	private User user;
}

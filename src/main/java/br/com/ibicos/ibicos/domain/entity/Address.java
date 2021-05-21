package br.com.ibicos.ibicos.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(exclude= {"person"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "idAddress",
		  scope = Address.class)
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idAddress;
	
	@NotBlank
	private String cep;
	
	@NotBlank
	private String street;
	
	@NotBlank
	private String numberAddress;
	
	@NotBlank
	private String neighborhood;	
	private String complement;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String state;
	
	@ToString.Exclude
	@JoinColumn(name="fk_id_person")
	@OneToOne
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Person person;

}

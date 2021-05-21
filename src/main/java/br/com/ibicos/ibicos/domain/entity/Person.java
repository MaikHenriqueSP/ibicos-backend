package br.com.ibicos.ibicos.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(exclude= {"address"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "idPerson",
		  scope = Person.class)
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPerson;
	
	@NotBlank
	private String namePerson;
	
	@NotNull
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate birthday;
	
	@NotBlank(message = "{org.hibernate.validator.constraints.br.CPF.message}")
	@Size(min = 11, max =11, message = "CPF size needs to be equal to 11 characters without any special character")
	@Column(updatable = false)
	private String cpf;
	
	@NotBlank(message="{org.hibernate.validator.constraints.br.CNPJ.message}")
	private String cnpj;
	
	@Valid
	@NotNull
	@OneToOne(mappedBy = "person", cascade = CascadeType.ALL, optional = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Address address;
	
	@PrePersist
	private void prePersist() {
	    if(address != null) {
	    	address.setPerson(this);
	    };
	}

}

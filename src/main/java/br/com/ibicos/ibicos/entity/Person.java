package br.com.ibicos.ibicos.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
	private LocalDate birthday;
	
	@NotBlank(message = "{org.hibernate.validator.constraints.br.CPF.message}")
	@Size(min = 11, max =11, message = "CPF sizes needs to be equal to 11 characters without any special character")
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

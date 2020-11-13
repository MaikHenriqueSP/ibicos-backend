package br.com.ibicos.ibicos.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "idPerson")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPerson;
	private String namePerson;
	private LocalDate birthday;
	private String cpf;
	private String cnpj;
	
	@OneToOne(mappedBy = "person", cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
	private Address address;
	
	@PrePersist
	private void prePersist() {
	    if(address != null) {
	    	address.setPerson(this);
	    };
	}

}

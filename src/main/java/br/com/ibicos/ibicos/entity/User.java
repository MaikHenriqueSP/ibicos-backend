package br.com.ibicos.ibicos.entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUser;
	
	@NotBlank
	@Size(min = 10)
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String passwordUser;
	
	@NotNull	
	private boolean notice;
	
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="fk_id_person", nullable = false)
	private Person person;
	
}

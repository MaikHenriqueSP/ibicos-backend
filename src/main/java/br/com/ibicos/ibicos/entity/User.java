package br.com.ibicos.ibicos.entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Column;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

import net.bytebuddy.utility.RandomString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idUser;
	
	@Email	
	@NotBlank
	@Size(min = 6)
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String passwordUser;
	
	@NotNull	
	private Boolean notice;
	
	private Boolean isAccountConfirmed;	
	
	@JsonIgnore
	@Column(updatable = false)
	private String validationToken;
	
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="fk_id_person", nullable = false)
	private Person person;
	
	@PrePersist	
	private void prePersist() {
		validationToken = RandomString.make(64);
		isAccountConfirmed = false;
	}
	
}

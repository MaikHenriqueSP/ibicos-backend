package br.com.ibicos.ibicos.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.utility.RandomString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private Integer id;
	
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
	
	@JsonIgnore
	private String accountRecoveryToken;
	
	@Valid
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="fk_id_person", nullable = false)
	private Person person;
	
	@PrePersist	
	private void prePersist() {
		validationToken = RandomString.make(64);
		accountRecoveryToken = RandomString.make(64);
		isAccountConfirmed = false;
	}
	
}

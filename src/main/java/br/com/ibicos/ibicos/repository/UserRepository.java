package br.com.ibicos.ibicos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ibicos.ibicos.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
	Optional<User> findUserByValidationToken(String validationToken);
	Optional<User> findByAccountRecoveryToken(String accountRecoveryToken);

}

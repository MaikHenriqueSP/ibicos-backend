package br.com.ibicos.ibicos.repository;

import br.com.ibicos.ibicos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
	Optional<User> findUserByValidationToken(String validationToken);
	Optional<User> findByAccountRecoveryToken(String accountRecoveryToken);	
	Boolean existsByEmail(String email);

}

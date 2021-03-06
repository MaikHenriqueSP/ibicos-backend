package br.com.ibicos.ibicos.domain.repository;

import br.com.ibicos.ibicos.domain.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
	Optional<Person> findByIdPerson(Integer idPerson);
}

package br.com.ibicos.ibicos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ibicos.ibicos.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
	Optional<Person> findByIdPerson(Integer idPerson);
}

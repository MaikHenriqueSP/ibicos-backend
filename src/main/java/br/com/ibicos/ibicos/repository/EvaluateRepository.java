package br.com.ibicos.ibicos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ibicos.ibicos.entity.Evaluate;

import java.util.List;

@Repository
public interface EvaluateRepository extends JpaRepository<Evaluate, Integer> {

    @Query(value = "SELECT DISTINCT * FROM evaluate WHERE fk_id_client = ?1", nativeQuery = true)
    List<Evaluate> findByCustomerId(Integer customerId);
}

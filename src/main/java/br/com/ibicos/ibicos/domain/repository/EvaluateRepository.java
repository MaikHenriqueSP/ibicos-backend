package br.com.ibicos.ibicos.domain.repository;

import br.com.ibicos.ibicos.domain.entity.Evaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluateRepository extends JpaRepository<Evaluate, Integer> {

    @Query(value = "SELECT DISTINCT * FROM evaluate WHERE fk_id_client = ?1 AND provider_evaluated = 0", nativeQuery = true)
    List<Evaluate> findByCustomerId(Integer customerId);

    @Query(value = "SELECT DISTINCT * FROM evaluate WHERE fk_id_provider = ?1 AND provider_evaluated = 0 AND hired = 1", nativeQuery = true)
    List<Evaluate> findByProviderIdNotEvaluatedAndHired(Integer providerId);
}

package br.com.ibicos.ibicos.repository;

import br.com.ibicos.ibicos.entity.CustomerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerStatisticsRepository extends JpaRepository<CustomerStatistics, Integer> {

    @Query("FROM CustomerStatistics cs WHERE cs.user.id = ?1")
    Optional<CustomerStatistics> findByUserId(Integer customerId);
}

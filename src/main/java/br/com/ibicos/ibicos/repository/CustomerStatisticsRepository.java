package br.com.ibicos.ibicos.repository;

import br.com.ibicos.ibicos.entity.CustomerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerStatisticsRepository extends JpaRepository<CustomerStatistics, Integer> {
}

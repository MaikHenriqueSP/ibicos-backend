package br.com.ibicos.ibicos.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ibicos.ibicos.entity.Statistics;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {
	Page<Statistics> findByUserId(Integer userId, Pageable pageable);
	Optional<Statistics> findByIdAndUserId(Integer statisticsId, Integer userId);
	
	@Query(value= "SELECT s.* FROM statistics as s, provider_statistics as p" + 
			" WHERE s.fk_id_user = ?1"
			+ " AND s.id_statistics != p.fk_id_statistics"
			, nativeQuery = true)	
	Optional<Statistics> findCustomerStatistic(Integer customerId);
}

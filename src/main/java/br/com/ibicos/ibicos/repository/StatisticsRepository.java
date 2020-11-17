package br.com.ibicos.ibicos.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ibicos.ibicos.entity.Statistics;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {
	Page<Statistics> findByUserId(Integer userId, Pageable pageable);
	Optional<Statistics> findByIdAndUserId(Integer statisticsId, Integer userId);
}

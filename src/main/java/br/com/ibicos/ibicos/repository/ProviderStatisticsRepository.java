package br.com.ibicos.ibicos.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ibicos.ibicos.entity.ProviderStatistics;

@Repository
public interface ProviderStatisticsRepository extends JpaRepository<ProviderStatistics, Integer>{
	Page<ProviderStatistics> findByStatisticsId(Integer statisticsId, Pageable pageable);
	Page<ProviderStatistics> findByCategoryId(Integer serviceCategoryId, Pageable pageable);
	Optional<ProviderStatistics> findByIdAndCategoryId(Integer providerStatisticsId, Integer serviceCategoryId);
	Optional<ProviderStatistics> findByIdAndStatisticsId(Integer providerStatisticsId, Integer statisticsId);
}

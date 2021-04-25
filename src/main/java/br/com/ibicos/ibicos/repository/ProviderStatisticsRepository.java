package br.com.ibicos.ibicos.repository;

import java.util.Optional;

import br.com.ibicos.ibicos.dto.ProviderSelfStatisticsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ibicos.ibicos.entity.ProviderStatistics;

@Repository
public interface ProviderStatisticsRepository extends JpaRepository<ProviderStatistics, Integer>{
	Page<ProviderStatistics> findByStatisticsId(Integer statisticsId, Pageable pageable);
	Page<ProviderStatistics> findByCategoryId(Integer serviceCategoryId, Pageable pageable);
	Optional<ProviderStatistics> findByIdAndCategoryId(Integer providerStatisticsId, Integer serviceCategoryId);
	Optional<ProviderStatistics> findByIdAndStatisticsId(Integer providerStatisticsId, Integer statisticsId);
	
	
	@Query(value = "SELECT DISTINCT ps.* FROM provider_statistics as ps, statistics "
			+ "WHERE ps.fk_id_service_category = ?1 "
			+ "AND ps.fk_id_statistics = statistics.id_statistics "
			+ "AND statistics.fk_id_user = ?2",
			nativeQuery = true)
	Optional<ProviderStatistics> findByServiceCategoryIdAndUserId(Integer serviceCategoryId, Integer userId);

	@Query(value = "SELECT new br.com.ibicos.ibicos.dto.ProviderSelfStatisticsDTO(AVG(st.evaluation), " +
			"SUM(st.evaluationsCounter), SUM(st.hiredServicesCounter), SUM(st.messagesCounter), SUM(pr.visualizations)) " +
			"FROM Statistics st JOIN ProviderStatistics pr ON st.id = pr.statistics.id WHERE st.user.id = ?1 GROUP BY st.user")
	Optional<ProviderSelfStatisticsDTO> findSelfStatisticsById(Integer providerId);
	
}

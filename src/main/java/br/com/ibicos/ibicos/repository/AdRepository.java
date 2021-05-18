package br.com.ibicos.ibicos.repository;

import java.util.List;
import java.util.Optional;

import br.com.ibicos.ibicos.view.AdView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ibicos.ibicos.dto.AdWithProviderStatisticsDTO;
import br.com.ibicos.ibicos.entity.Ad;

import javax.persistence.Tuple;

@Repository
public interface AdRepository extends PagingAndSortingRepository<Ad, Integer>{
	@Query(value = "SELECT new br.com.ibicos.ibicos.view.AdView(ad, pr) FROM Ad ad, ProviderStatistics pr, Statistics st " +
			"WHERE ad.user.id = ?1 AND ad.serviceCategory.id = pr.category.id AND st.id = pr.id")
	Page<AdView> findByUserId(Integer userId, Pageable pageable);
	Optional<Ad> findByIdAndUserId(Integer adId, Integer userId);

	@Query(value = "SELECT DISTINCT new br.com.ibicos.ibicos.view.AdView(ad, pr) FROM Ad ad, ProviderStatistics pr, AdRegionArea adra  " +
			" JOIN AdCity ac ON ac.ad.id = ad.id " +
			" JOIN ServiceCategory sc ON ad.serviceCategory.id = sc.id  WHERE" +
			" ad.user.id = pr.user.id " +
			" AND pr.category.id = ad.serviceCategory.id " +
			" AND adra.adCity.idCity = ac.idCity" +
			" AND ac.cityName LIKE %:cityName% " +
			" AND sc.categoryName LIKE %:categoryName% " +
			" AND ad.serviceCategory.id = sc.id" +
			" AND adra.areaName LIKE %:areaName% " +
			" AND ac.stateAbb LIKE %:stateName% ")
	Page<AdView> listAdProjections(@Param("categoryName") String categoryName,
								   @Param("stateName") String stateName,
								   @Param("cityName") String cityName,
								   @Param("areaName") String areaName,
								   Pageable pageable);

}

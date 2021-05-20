package br.com.ibicos.ibicos.repository;

import br.com.ibicos.ibicos.entity.Ad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdRepository extends PagingAndSortingRepository<Ad, Integer>{
	@Query(value = "SELECT ad FROM Ad ad, ProviderStatistics pr, Statistics st " +
			"WHERE ad.user.id = ?1 AND ad.serviceCategory.id = pr.category.id AND st.id = pr.id")
	Page<Ad> findByUserId(Integer userId, Pageable pageable);
	Optional<Ad> findByIdAndUserId(Integer adId, Integer userId);

	@Query("SELECT DISTINCT ad FROM Ad ad, AdRegionArea adra JOIN ad.providerStatistics "+
			" JOIN AdCity ac ON ac.ad.id = ad.id " +
			" JOIN ServiceCategory sc ON ad.serviceCategory.id = sc.id  WHERE" +
			" adra.adCity.idCity = ac.idCity" +
			" AND ac.cityName LIKE %:cityName% " +
			" AND sc.categoryName LIKE %:categoryName% " +
			" AND ad.serviceCategory.id = sc.id" +
			" AND adra.areaName LIKE %:areaName% " +
			" AND ac.stateAbb LIKE %:stateName% ")
	Page<Ad> listAdProjections(@Param("categoryName") String categoryName,
								   @Param("stateName") String stateName,
								   @Param("cityName") String cityName,
								   @Param("areaName") String areaName,
								   Pageable pageable);

}

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
	Page<Ad> findByUserId(Integer userId, Pageable pageable);
	Optional<Ad> findByIdAndUserId(Integer adId, Integer userId);
	
	
	@Query(value = "SELECT DISTINCT ad.* "
			+ "FROM ad, "
			+ "ad_city as city, "
			+ "service_category as category, "
			+ "ad_region_area as area, "
			+ "provider_statistics,"
			+ "statistics "		+ 			
			"	WHERE " +
			"    city.state_abb LIKE %?2%    " +
			"    AND ad.id_ad = city.fk_id_ad " + 
			"    AND city.city_name LIKE %?3% " + 
			"    AND ad.fk_id_service_category = category.id_service_category " + 
			"    AND category.category_name LIKE %?1% " + 
			"    AND area.fk_id_city = city.id_city " + 
		    "    AND area.area_name LIKE %?4%" + 
		    "    AND provider_statistics.fk_id_service_category = category.id_service_category" + 
		    "    AND provider_statistics.fk_id_statistics = statistics.id_statistics" +
		    "    AND ad.fk_id_user = statistics.fk_id_user" ,nativeQuery = true)
	Page<Ad> findByAndSortByMultiQueryFilter(
			String categoryName, 
			String stateName,
			String cityName, 
			String areaName,
			Pageable pageable);
		
	@Query(value="SELECT DISTINCT ad.id_ad as idAd, " + 
			"				ad.ad_description as adDescription," + 
			"                category.category_name as categoryName," + 
			"                statistics.evaluation as evaluation," + 
			"                statistics.evaluations_counter as evaluationsCounter," + 
			"                provider_statistics.visualizations as visualizations," + 
			"                statistics.fk_id_user as idUser" + 
			"			 FROM ad, " + 
			"			 ad_city as city, " + 
			"			 service_category as category, " + 
			"			 ad_region_area as area, " + 
			"			 provider_statistics," + 
			"			 statistics 		 			" + 
			"				WHERE  " + 
			"			    city.state_abb LIKE %?2%     " + 
			"			    AND ad.id_ad = city.fk_id_ad   " + 
			"			    AND city.city_name LIKE %?3%   " + 
			"			    AND ad.fk_id_service_category = category.id_service_category   " + 
			"			    AND category.category_name LIKE %?1% "+ 
			"			    AND area.fk_id_city = city.id_city   " + 
			"		        AND area.area_name LIKE %?4%  " + 
			"		        AND provider_statistics.fk_id_service_category = category.id_service_category  " + 
			"		        AND provider_statistics.fk_id_statistics = statistics.id_statistics " + 
			"		        AND ad.fk_id_user = statistics.fk_id_user", nativeQuery = true)
	List<AdWithProviderStatisticsDTO> listAdWithFilters(String categoryName, String stateName, String cityName, String areaName);

	@Query(value = "SELECT DISTINCT ad.* "
			+ "FROM ad, "
			+ "ad_city as city, "
			+ "service_category as category, "
			+ "ad_region_area as area, "
			+ "provider_statistics,"
			+ "statistics "		+
			"	WHERE " +
			"    city.state_abb LIKE %?2%    " +
			"    AND ad.id_ad = city.fk_id_ad " +
			"    AND city.city_name LIKE %?3% " +
			"    AND ad.fk_id_service_category = category.id_service_category " +
			"    AND category.category_name LIKE %?1% " +
			"    AND area.fk_id_city = city.id_city " +
			"    AND area.area_name LIKE %?4%" +
			"    AND provider_statistics.fk_id_service_category = category.id_service_category" +
			"    AND provider_statistics.fk_id_statistics = statistics.id_statistics" +
			"    AND ad.fk_id_user = statistics.fk_id_user" ,nativeQuery = true)
	List<Tuple> findByAndSortByMultiQueryFilterEmbedded(String categoryName, String stateName, String cityName, String areaName);

//	@Query(value="SELECT ad.ad_description AS AdDescription, service_category.category_name AS CategoryName  FROM ad, service_category WHERE ad.fk_id_service_category = service_category.id_service_category", nativeQuery = true)
//	@Query(value="SELECT ad, sc FROM br.com.ibicos.ibicos.entity.Ad ad, br.com.ibicos.ibicos.entity.ServiceCategory sc")

	@Query(value = "SELECT new br.com.ibicos.ibicos.view.AdView(ad, pr) FROM Ad ad, ProviderStatistics pr WHERE ad.user.id = pr.statistics.user.id" )
	List<AdView> listAdProjections();


//	@Query(value = "SELECT DISTINCT new br.com.ibicos.ibicos.view.AdView(ad, pr) FROM Ad ad, ProviderStatistics pr, AdCity ac, ServiceCategory sc, AdRegionArea  adr " +
//			"WHERE ad.user.id = pr.statistics.user.id" +
//			" AND ac.cityName LIKE %:cityName% " +
//			" AND sc.categoryName LIKE %:categoryName% " +
//			" AND ad.serviceCategory.id = sc.id" +
//			" AND adr.areaName LIKE %:areaName% " +
//			" AND ac.stateAbb LIKE %:stateName% " )
	@Query(value = "SELECT DISTINCT new br.com.ibicos.ibicos.view.AdView(ad, pr) FROM Ad ad, ProviderStatistics pr, AdRegionArea adra  " +
//			" JOIN ProviderStatistics pr ON ad.user.id = pr.statistics.user.id " +
			" JOIN AdCity ac ON ac.ad.id = ad.id " +
			" JOIN ServiceCategory sc ON ad.serviceCategory.id = sc.id  WHERE" +
			" ad.user.id = pr.statistics.user.id " +
			" AND pr.category.id = ad.serviceCategory.id " +

			" AND adra.adCity.idCity = ac.idCity" +
			" AND ac.cityName LIKE %:cityName% " +
			" AND sc.categoryName LIKE %:categoryName% " +
			" AND ad.serviceCategory.id = sc.id" +
			" AND adra.areaName LIKE %:areaName% " +
			" AND ac.stateAbb LIKE %:stateName% " )
	List<AdView> listAdProjections(@Param("categoryName") String categoryName,
								   @Param("stateName") String stateName,
								   @Param("cityName") String cityName,
								   @Param("areaName") String areaName);

}

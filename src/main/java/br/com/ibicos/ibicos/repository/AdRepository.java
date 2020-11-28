package br.com.ibicos.ibicos.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.ibicos.ibicos.entity.Ad;

@Repository
public interface AdRepository extends PagingAndSortingRepository<Ad, Integer>{
	Page<Ad> findByUserId(Integer userId, Pageable pageable);
	Optional<Ad> findByIdAndUserId(Integer adId, Integer userId);
	
	@Query(value = "SELECT DISTINCT ad.* FROM ad, ad_city as city, service_category as category, ad_region_area as area \n" + 
			"	WHERE ad.id_ad = city.fk_id_ad \n" + 
			"    AND city_name LIKE %?2% \n" + 
			"    AND ad.fk_id_service_category = category.id_service_category " + 
			"    AND category_name LIKE %?1% \n" + 
			"    AND area.fk_id_city = city.id_city " + 
		    "    AND area.area_name LIKE %?3%" ,nativeQuery = true)
	Page<Ad> findByAndSortByMultiQueryFilter(String categoryName,
			String cityName, String areaName ,Pageable pageable);
	
}

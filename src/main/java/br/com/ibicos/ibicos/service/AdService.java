package br.com.ibicos.ibicos.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.ibicos.ibicos.enums.AdSortByValues;
import br.com.ibicos.ibicos.mapper.AdWithProviderStatisticsMapper;
import br.com.ibicos.ibicos.view.AdView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ibicos.ibicos.dto.AdWithProviderStatisticsDTO;
import br.com.ibicos.ibicos.entity.Ad;
import br.com.ibicos.ibicos.entity.ServiceCategory;
import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.repository.AdRepository;

@Service
public class AdService {

	@Autowired
	private AdRepository adRepository;
	
	@Autowired
	private ProviderStatisticsService providerStatisticsService;

	@Autowired
	private AdWithProviderStatisticsMapper adWithProviderStatisticsMapper;

	public Iterable<Ad> listAds() {
		return adRepository.findAll();
	}
	
	public Page<Ad> listProviderAds(Integer providerId, Pageable pageable) {
		return adRepository.findByUserId(providerId, pageable);
	}
	
	public Optional<Ad> showAdByIdAndProviderId(Integer adId, Integer providerId) {
		return adRepository.findByIdAndUserId(adId, providerId);
	}
	
	public Ad updateAd(Ad oldAd, Ad ad) {
		if (ad.getAdDescription() != null) {
			oldAd.setAdDescription(ad.getAdDescription());
		}

		if (ad.getCities() != null) {
			oldAd.setCities(ad.getCities());
		}

		if (ad.getServiceCategory() != null) {
			oldAd.setServiceCategory(ad.getServiceCategory());
		}

		return adRepository.save(oldAd);
	}

	public Page<AdWithProviderStatisticsDTO> listAdsByFilters(String categoryName, String stateName, String cityName, String areaName, String sortByFieldName, int page, int size) {
		JpaSort providerStatisticsJpaSort = getJpaSortProviderStatistics(sortByFieldName);
		PageRequest pageRequest= getPageRequest(page, size, providerStatisticsJpaSort);
		Page<AdView> adViews = adRepository.listAdProjections(categoryName, stateName, cityName, areaName, pageRequest);

		List<AdView> adViewsList = adViews.getContent();
		List<AdWithProviderStatisticsDTO> adWithProviderStatisticsDTOS = getAdWithProviderStatisticsDTOS(adViewsList);

		return new PageImpl<>(adWithProviderStatisticsDTOS, pageRequest, adViews.getTotalElements());
	}

	private PageRequest getPageRequest(int page, int size, JpaSort jpaSort) {
		if (jpaSort == null) {
			return PageRequest.of(page, size);
		}

		return PageRequest.of(page, size, jpaSort);
	}

	private JpaSort getJpaSortProviderStatistics(String sortByFieldName) {
		if (isSortFieldNameValid(sortByFieldName)) {
			String providerStatisticsQueryAlias = "pr";
			String sortByQueryComplement = "(" + providerStatisticsQueryAlias + ".statistics." + sortByFieldName + ")";
			return JpaSort.unsafe(Sort.Direction.DESC, sortByQueryComplement);
		}
		return null;
	}

	private boolean isSortFieldNameValid(String sortByFieldName) {
		return Arrays.stream(AdSortByValues.values()).anyMatch(AdSortEnum -> AdSortEnum.label.equals(sortByFieldName));
	}

	private List<AdWithProviderStatisticsDTO> getAdWithProviderStatisticsDTOS(List<AdView> adViews) {
		return adViews.stream().map(adView -> adWithProviderStatisticsMapper.AdViewToAdWithProviderStatisticsDTO(adView))
						.collect(Collectors.toList());
	}

	@Transactional(rollbackFor = { RuntimeException.class})
	public Ad createAd(Ad ad) {
		Ad savedAd =  adRepository.save(ad);		
		
		User adCreator = savedAd.getUser();
		ServiceCategory adCategory = savedAd.getServiceCategory();
		
		providerStatisticsService.createProviderStatisticsIfItNotExists(adCreator, adCategory);		
		
		return savedAd;
	}
	
	public void deleteAdById(Integer id) {
		adRepository.deleteById(id);
	}
	

}

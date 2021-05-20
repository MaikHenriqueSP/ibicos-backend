package br.com.ibicos.ibicos.service;

import br.com.ibicos.ibicos.dto.AdDTO;
import br.com.ibicos.ibicos.domain.entity.Ad;
import br.com.ibicos.ibicos.domain.entity.ServiceCategory;
import br.com.ibicos.ibicos.domain.entity.User;
import br.com.ibicos.ibicos.enums.AdSortByValues;
import br.com.ibicos.ibicos.mapper.AdMapper;
import br.com.ibicos.ibicos.repository.AdRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdService {

	private final AdRepository adRepository;
	private final ProviderStatisticsService providerStatisticsService;
	private final AdMapper adMapper;

	public AdService(AdRepository adRepository, ProviderStatisticsService providerStatisticsService, AdMapper adMapper) {
		this.adRepository = adRepository;
		this.providerStatisticsService = providerStatisticsService;
		this.adMapper = adMapper;
	}

	public Page<AdDTO> listProviderAds(Integer providerId, Pageable pageable) {
		Page<Ad> adViews = adRepository.findByUserId(providerId, pageable);
		List<Ad> adViewsList = adViews.getContent();
		List<AdDTO> adWithProviderStatisticsDTOList = getAdWithProviderStatisticsDTOS(adViewsList);
		return new PageImpl<>(adWithProviderStatisticsDTOList, pageable, adViewsList.size());
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

	public Page<AdDTO> listAdsByFilters(String categoryName, String stateName, String cityName, String areaName,
										String sortByFieldName, int page, int size) {

		PageRequest pageRequest = getProviderStatisticsPageRequest(page, size, sortByFieldName);
		Page<Ad> adViews = adRepository.listAdProjections(categoryName, stateName, cityName, areaName, pageRequest);

		List<Ad> adViewsList = adViews.getContent();
		List<AdDTO> adWithProviderStatisticsDTOS = getAdWithProviderStatisticsDTOS(adViewsList);

		return new PageImpl<>(adWithProviderStatisticsDTOS, pageRequest, adViews.getTotalElements());
	}

	private PageRequest getProviderStatisticsPageRequest(int page, int size, String fieldName) {
		if (isSortFieldNameValid(fieldName)) {
			String providerStatisticsQueryAlias = "providerStatistics." + fieldName;
			return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, providerStatisticsQueryAlias));
		}
		return PageRequest.of(page, size);
	}

	private boolean isSortFieldNameValid(String sortByFieldName) {
		return Arrays.stream(AdSortByValues.values()).anyMatch(AdSortEnum -> AdSortEnum.label.equals(sortByFieldName));
	}

	private List<AdDTO> getAdWithProviderStatisticsDTOS(List<Ad> ads) {
		return ads.stream().map(adMapper::AdToAdDTO)
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

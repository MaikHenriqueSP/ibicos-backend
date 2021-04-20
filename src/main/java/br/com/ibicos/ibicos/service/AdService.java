package br.com.ibicos.ibicos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.ibicos.ibicos.mapper.AdWithProviderStatisticsMapper;
import br.com.ibicos.ibicos.view.AdView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ibicos.ibicos.dto.AdWithProviderStatisticsDTO;
import br.com.ibicos.ibicos.entity.Ad;
import br.com.ibicos.ibicos.entity.ServiceCategory;
import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.repository.AdRepository;

import javax.persistence.Tuple;

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
			System.out.println(ad.getCities());
			oldAd.setCities(ad.getCities());
		}
		
		if (ad.getServiceCategory() != null) {
			oldAd.setServiceCategory(ad.getServiceCategory());
		}
		
		Ad updatedAd = adRepository.save(oldAd);
		
		return updatedAd;
	}
	
	public Page<AdWithProviderStatisticsDTO> listAdsByFilters(String categoryName, String stateName, String cityName, String areaName, int page, int size) {
		PageRequest pageRequest= PageRequest.of(page, size);
		Page<AdView> adViews = adRepository.listAdProjections(categoryName, stateName, cityName, areaName, pageRequest);

		List<AdView> adViewsList = adViews.getContent();
		List<AdWithProviderStatisticsDTO> adWithProviderStatisticsDTOS = getAdWithProviderStatisticsDTOS(adViewsList);

		Page<AdWithProviderStatisticsDTO> adWithProviderStatisticsDTOPage = new PageImpl<>(adWithProviderStatisticsDTOS, pageRequest, adViews.getTotalElements());

		return adWithProviderStatisticsDTOPage;
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

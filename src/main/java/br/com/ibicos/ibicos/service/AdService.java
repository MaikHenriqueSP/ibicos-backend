package br.com.ibicos.ibicos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ibicos.ibicos.entity.Ad;
import br.com.ibicos.ibicos.repository.AdRepository;

@Service
public class AdService {

	@Autowired
	private AdRepository adRepository;
	
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
}
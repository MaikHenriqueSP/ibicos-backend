package br.com.ibicos.ibicos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibicos.ibicos.entity.Ad;
import br.com.ibicos.ibicos.service.AdService;
import br.com.ibicos.ibicos.service.ProviderStatisticsService;

@RestController
@RequestMapping("/api/v1/ad")
public class AdController {
	@Autowired
	private AdService adService;
	
	@Autowired
	private ProviderStatisticsService providerStatisticsService;
	
	@GetMapping("/list/ad")
	public ResponseEntity<?> listAllAds(){
		Iterable<Ad> ads = adService.listAds();
		return ResponseEntity.ok().body(ads);
	}
	
	@GetMapping("/list/ad/filter")
	public ResponseEntity<?> listAdsByFilters(
			@RequestParam(defaultValue = "") String categoryName,
			@RequestParam(defaultValue = "") String stateName,
			@RequestParam(defaultValue = "") String cityName,
			@RequestParam(defaultValue = "") String areaName,
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "8") int size) {
		Page<Ad> pageableAdsList = adService.listAdsByFilters(categoryName, stateName, cityName, areaName, page, size);
		return ResponseEntity.ok(pageableAdsList);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createAd(@RequestBody Ad ad) {
		Ad savedAd = adService.createAd(ad);
		return ResponseEntity.ok(savedAd);
	}
}

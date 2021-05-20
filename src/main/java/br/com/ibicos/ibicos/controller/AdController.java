package br.com.ibicos.ibicos.controller;

import br.com.ibicos.ibicos.dto.AdDTO;
import br.com.ibicos.ibicos.entity.Ad;
import br.com.ibicos.ibicos.service.AdService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ads")
public class AdController {

	private final AdService adService;

	public AdController(AdService adService) {
		this.adService = adService;
	}


	@GetMapping
	public ResponseEntity<?> listAdsByFilters(
			@RequestParam(defaultValue = "") String categoryName,
			@RequestParam(defaultValue = "") String stateName,
			@RequestParam(defaultValue = "") String cityName,
			@RequestParam(defaultValue = "") String areaName,
			@RequestParam(defaultValue = "", name = "sortBy") String sortByFieldName,
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "8") int size) {
		Page<AdDTO> pageableAdsList = adService.listAdsByFilters(categoryName, stateName, cityName, areaName,
				sortByFieldName, page, size);
		return ResponseEntity.ok(pageableAdsList);
	}
	
	@PostMapping
	public ResponseEntity<?> createAd(@Valid @RequestBody Ad ad) {
		Ad savedAd = adService.createAd(ad);
		return ResponseEntity.ok(savedAd);
	}
	

	@DeleteMapping("/{adId}")
	public ResponseEntity<?> deleteAd(@PathVariable Integer adId) {
		adService.deleteAdById(adId);
		return ResponseEntity.ok(Map.of("message", "ad deleted"));
	}


}

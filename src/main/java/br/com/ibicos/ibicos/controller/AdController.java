package br.com.ibicos.ibicos.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Tuple;
import javax.validation.Valid;

import br.com.ibicos.ibicos.mapper.AdWithProviderStatisticsMapper;
import br.com.ibicos.ibicos.view.AdView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibicos.ibicos.dto.AdWithProviderStatisticsDTO;
import br.com.ibicos.ibicos.entity.Ad;
import br.com.ibicos.ibicos.repository.AdRepository;
import br.com.ibicos.ibicos.service.AdService;
import br.com.ibicos.ibicos.service.ProviderStatisticsService;

@RestController
@RequestMapping("/api/v1/ad")
public class AdController {
	@Autowired
	private AdService adService;

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
		Page<AdWithProviderStatisticsDTO> pageableAdsList = adService.listAdsByFilters(categoryName, stateName, cityName, areaName, page, size);
		return ResponseEntity.ok(pageableAdsList);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createAd(@Valid @RequestBody Ad ad) {
		Ad savedAd = adService.createAd(ad);
		return ResponseEntity.ok(savedAd);
	}
	

	@DeleteMapping("/ad/delete/{id}")
	public ResponseEntity<?> deleteAd(@PathVariable Integer id) {
		adService.deleteAdById(id);
		return ResponseEntity.ok(Map.of("message", "ad deleted"));
	}


	@Autowired
	private AdWithProviderStatisticsMapper adWithProviderStatisticsMapper;

}

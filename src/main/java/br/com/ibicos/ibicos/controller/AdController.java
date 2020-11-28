package br.com.ibicos.ibicos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibicos.ibicos.entity.Ad;
import br.com.ibicos.ibicos.service.AdService;

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

}

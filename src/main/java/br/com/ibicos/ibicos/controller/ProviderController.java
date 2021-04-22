package br.com.ibicos.ibicos.controller;

import java.util.List;
import java.util.Map;

import br.com.ibicos.ibicos.dto.IncrementViewsRequestDTO;
import br.com.ibicos.ibicos.entity.Evaluate;
import br.com.ibicos.ibicos.service.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibicos.ibicos.entity.Ad;
import br.com.ibicos.ibicos.entity.Statistics;
import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.repository.AdRepository;

@RestController
@RequestMapping("/api/v1/provider")
public class ProviderController {

	@Autowired
	private ProviderService providerService;

	@Autowired
	private StatisticsService statisticsService;

	@Autowired
	private ProviderStatisticsService providerStatisticsService;

	@Autowired
	private AdService adService;
	
	@Autowired
	private AdRepository adRepository;

	@Autowired
	private EvaluateService evaluateService;

	@GetMapping
	public ResponseEntity<?> listProviders() {
		List<User> providers = providerService.listProviders();

		return ResponseEntity.status(HttpStatus.OK).body(providers);
	}

	@GetMapping(path = "/{providerId}/ad")
	public ResponseEntity<?> listAds(@PathVariable("providerId") Integer providerId, Pageable pageable) {
		Page<Ad> providerAds = adService.listProviderAds(providerId, pageable);

		return ResponseEntity.status(HttpStatus.OK).body(providerAds);
	}

	@GetMapping(path = "/{userId}/statistics")
	public ResponseEntity<?> listProviderStatistics(@PathVariable("userId") Integer userId, Pageable pageable) {
		Page<Statistics> userStatistics = statisticsService.listStatisticsByUserId(userId, pageable);

		return ResponseEntity.status(HttpStatus.OK).body(userStatistics);
	}

	@PutMapping(path = "{providerId}/ad/{adId}")
	public ResponseEntity<?> changeProviderAd(@PathVariable("providerId") Integer providerId,
			@PathVariable("adId") Integer adId, @RequestBody Ad ad) {
		Ad oldAd = adService.showAdByIdAndProviderId(adId, providerId).orElse(null);
		User provider = providerService.showProvider(providerId).orElse(null);
		
		if (provider == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Provider with provided id not found.");
		} else if (oldAd == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ad with provided id not found.");
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(adService.updateAd(oldAd, ad));
	}

	@GetMapping("/evaluate/{providerId}/pending/evaluation")
	public ResponseEntity<?> listProviderPendingEvaluations(@PathVariable Integer providerId) {
		List<Evaluate> pendingEvaluations = evaluateService.listEvaluationsByProviderId(providerId);
		return ResponseEntity.ok(Map.of("pendingEvaluations", pendingEvaluations));
	}

	@PutMapping("/evaluate/customer")
	public ResponseEntity<?> evaluateCustomer(@RequestBody ObjectNode objectNode) {
		Integer idEvaluate = objectNode.get("id_evaluate").asInt();
		Float evaluation = objectNode.get("evaluation").floatValue();

		evaluateService.evaluateCustomer(idEvaluate, evaluation);
		return ResponseEntity.ok().body(Map.of(
				"message", "Provider successfully evaluated",
				"status", HttpStatus.OK.value()
		));
	}

	@PutMapping("/evaluate/views/increment-counter")
	public ResponseEntity<?> incrementProviderViews(@RequestBody IncrementViewsRequestDTO incrementViewsRequestDTO) {
		evaluateService.incrementProviderVisualizations(incrementViewsRequestDTO);
		return ResponseEntity.ok().build();
	}
}

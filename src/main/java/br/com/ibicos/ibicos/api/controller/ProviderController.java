package br.com.ibicos.ibicos.api.controller;

import br.com.ibicos.ibicos.dto.AdDTO;
import br.com.ibicos.ibicos.dto.IncrementViewsRequestDTO;
import br.com.ibicos.ibicos.domain.entity.Ad;
import br.com.ibicos.ibicos.domain.entity.Evaluate;
import br.com.ibicos.ibicos.domain.entity.Statistics;
import br.com.ibicos.ibicos.domain.entity.User;
import br.com.ibicos.ibicos.domain.service.AdService;
import br.com.ibicos.ibicos.domain.service.EvaluateService;
import br.com.ibicos.ibicos.domain.service.ProviderService;
import br.com.ibicos.ibicos.domain.service.StatisticsService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/providers")
public class ProviderController {

	private final ProviderService providerService;
	private final StatisticsService statisticsService;
	private final AdService adService;
	private final EvaluateService evaluateService;

	public ProviderController(ProviderService providerService, StatisticsService statisticsService, AdService adService, EvaluateService evaluateService) {
		this.providerService = providerService;
		this.statisticsService = statisticsService;
		this.adService = adService;
		this.evaluateService = evaluateService;
	}

	@GetMapping
	public ResponseEntity<?> listProviders() {
		List<User> providers = providerService.listProviders();

		return ResponseEntity.status(HttpStatus.OK).body(providers);
	}

	@GetMapping(path = "/{providerId}/ads")
	public ResponseEntity<?> listAds(@PathVariable("providerId") Integer providerId, Pageable pageable) {
		Page<AdDTO> providerAds = adService.listProviderAds(providerId, pageable);

		return ResponseEntity.status(HttpStatus.OK).body(providerAds);
	}

	@GetMapping(path = "/{userId}/statistics")
	public ResponseEntity<?> listProviderStatistics(@PathVariable("userId") Integer userId, Pageable pageable) {
		Page<Statistics> userStatistics = statisticsService.listStatisticsByUserId(userId, pageable);

		return ResponseEntity.status(HttpStatus.OK).body(userStatistics);
	}

	@PutMapping(path = "{providerId}/ads/{adId}")
	public ResponseEntity<?> changeProviderAd(@PathVariable("providerId") Integer providerId,
			@PathVariable("adId") Integer adId, @RequestBody Ad ad) {
		Ad oldAd = adService.showAdByIdAndProviderId(adId, providerId).orElse(null);
		User provider = providerService.showProvider(providerId).orElse(null);
		
		if (provider == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Provider with provided id not found.");
		}

		if (oldAd == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ad with provided id not found.");
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(adService.updateAd(oldAd, ad));
	}

	@GetMapping("/evaluate/{providerId}/pending-evaluation")
	public ResponseEntity<?> listProviderPendingEvaluations(@PathVariable Integer providerId) {
		List<Evaluate> pendingEvaluations = evaluateService.listEvaluationsByProviderId(providerId);
		return ResponseEntity.ok(Map.of("pendingEvaluations", pendingEvaluations));
	}

	@PutMapping("/evaluate/customers")
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

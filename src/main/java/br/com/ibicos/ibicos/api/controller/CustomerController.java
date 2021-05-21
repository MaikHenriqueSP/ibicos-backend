package br.com.ibicos.ibicos.api.controller;

import br.com.ibicos.ibicos.api.dto.CustomerEmailToProviderDTO;
import br.com.ibicos.ibicos.api.dto.CustomerSelfStatisticsDTO;
import br.com.ibicos.ibicos.domain.entity.Evaluate;
import br.com.ibicos.ibicos.domain.service.CustomerService;
import br.com.ibicos.ibicos.domain.service.EvaluateService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class CustomerController {

//	private final CustomerService customerService;
	private final EvaluateService evaluateService;

	@PutMapping("/evaluate/provider")
	public ResponseEntity<?> evaluateProvider(@RequestBody ObjectNode objectNode) {
		Integer idEvaluate = objectNode.get("id_evaluate").asInt();
		Float evaluation = objectNode.get("evaluation").floatValue();

		evaluateService.evaluateProvider(idEvaluate, evaluation);
		return ResponseEntity.ok().body(Map.of(
					"message", "Provider successfully evaluated",
					"status", HttpStatus.OK.value()
					));
	}
	
	@PutMapping("/evaluate/provider/confirm-hiring")
	public ResponseEntity<?> evaluateProviderJobConfirmation(@RequestBody ObjectNode objectNode) {
		Integer idEvaluate = objectNode.get("id_evaluate").asInt();

		evaluateService.evaluateProviderJobConfirmation(idEvaluate);
		return ResponseEntity.ok().body(
				Map.of("message", "Job hiring successfully confirmed",
						"status", HttpStatus.OK.value())
				);
	}

//	@PostMapping("/ad/send-message")
//	public ResponseEntity<?> sendEmailToProvider(@RequestBody CustomerEmailToProviderDTO customerEmailToProviderDTO ) {
//		customerService.sendEmailToProvider(customerEmailToProviderDTO);
//		return ResponseEntity.ok(Map.of("message", "email successfully sent"));
//	}

	@GetMapping("/evaluate/{customerId}/pending-evaluations")
	public ResponseEntity<?> listCustomerPendingEvaluations(@PathVariable Integer customerId ) {
		List<Evaluate> pendingEvaluations = evaluateService.listEvaluationsByCustomerId(customerId);
		return ResponseEntity.ok(Map.of("pendingEvaluations", pendingEvaluations));
	}

	@DeleteMapping("/evaluate/{idEvaluate}")
	public ResponseEntity<?> deletePendingEvaluation(@PathVariable Integer idEvaluate) {
		evaluateService.deleteEvaluationById(idEvaluate);
		return ResponseEntity.ok(Map.of("message", "pending evaluation successfully deleted"));
	}

//	@GetMapping("/statistics/{customerId}/user-data")
//	public ResponseEntity<?> getProviderSelfStatistics(@PathVariable Integer customerId) {
//
//		CustomerSelfStatisticsDTO providerSelfStatistics = customerService.getCustomerSelfStatisticsById(customerId);
//
//		return ResponseEntity.ok().body(Map.of("customer_statistics", providerSelfStatistics));
//	}
	
}

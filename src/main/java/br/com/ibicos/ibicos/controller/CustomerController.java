package br.com.ibicos.ibicos.controller;

import java.util.List;
import java.util.Map;

import br.com.ibicos.ibicos.entity.Evaluate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.ibicos.ibicos.dto.CustomerEmailToProviderDTO;
import br.com.ibicos.ibicos.entity.Statistics;
import br.com.ibicos.ibicos.service.CustomerService;
import br.com.ibicos.ibicos.service.EvaluateService;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService; 
	
	@Autowired
	private EvaluateService evaluateService;
	
	@GetMapping("/myNumbers/{customerId}")
	public ResponseEntity<?> showCustomerStatistics(@PathVariable Integer customerId) {
		
		Statistics customerStatistics = customerService
				.showCustomerStatistics(customerId);
		
		return ResponseEntity.ok()
				.body(Map.of("statistics", customerStatistics));
	}
	
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
	
	@PutMapping("/evaluate/provider/confirmHiring")
	public ResponseEntity<?> evaluateProviderJobConfirmation(@RequestBody ObjectNode objectNode) {
		Integer idEvaluate = objectNode.get("id_evaluate").asInt();

		evaluateService.evaluateProviderJobConfirmation(idEvaluate);
		return ResponseEntity.ok().body(
				Map.of("message", "Job hiring successfully confirmed",
						"status", HttpStatus.OK.value())
				);
	}

	@PostMapping("/ad/sendMessage")
	public ResponseEntity<?> sendEmailToProvider(@RequestBody CustomerEmailToProviderDTO customerEmailToProviderDTO ) {
		customerService.sendEmailToProvider(customerEmailToProviderDTO);
		return ResponseEntity.ok(Map.of("message", "email successfully sent"));
	}

	@GetMapping("/evaluate/{customerId}/pending/evaluation")
	public ResponseEntity<?> listCustomerPendingEvaluations(@PathVariable Integer customerId ) {
		List<Evaluate> pendingEvaluations = evaluateService.listEvaluationsByCustomerId(customerId);
		return ResponseEntity.ok(Map.of("pendingEvaluations", pendingEvaluations));
	}

	@DeleteMapping("/evaluate/delete/{idEvaluate}")
	public ResponseEntity<?> deletePendingEvaluation(@PathVariable Integer idEvaluate) {
		evaluateService.deleteEvaluationById(idEvaluate);
		return ResponseEntity.ok(Map.of("message", "pending evaluation successfully deleted"));
	}
	
	
}

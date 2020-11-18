package br.com.ibicos.ibicos.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibicos.ibicos.entity.Person;
import br.com.ibicos.ibicos.entity.Statistics;
import br.com.ibicos.ibicos.repository.StatisticsRepository;
import br.com.ibicos.ibicos.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService; 
	
	@Autowired
	private StatisticsRepository statisticsRepository;
	
	@GetMapping("/myNumbers/{customerId}")
	public ResponseEntity<?> showStatistics(@PathVariable Integer customerId) {
		Optional<Statistics> optionalStatistic = statisticsRepository
				.findCustomerStatistic(customerId);
		
		return ResponseEntity.ok(optionalStatistic.get());
	}
	
	
	

}

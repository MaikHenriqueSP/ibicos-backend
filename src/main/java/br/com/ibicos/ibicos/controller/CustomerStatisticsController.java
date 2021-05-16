package br.com.ibicos.ibicos.controller;

import br.com.ibicos.ibicos.entity.CustomerStatistics;
import br.com.ibicos.ibicos.entity.Statistics;
import br.com.ibicos.ibicos.service.CustomerService;
import br.com.ibicos.ibicos.service.CustomerStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/customers-statistics")
public class CustomerStatisticsController {

    private final CustomerStatisticsService customerStatiticsService;

    public CustomerStatisticsController(CustomerStatisticsService customerStatiticsService) {
        this.customerStatiticsService = customerStatiticsService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerStatistics(@PathVariable Integer customerId) {

        Statistics customerStatistics = customerStatiticsService
                .getCustomerStatistics(customerId);

        return ResponseEntity.ok()
                .body(Map.of("customer-statistics", customerStatistics));
    }
}

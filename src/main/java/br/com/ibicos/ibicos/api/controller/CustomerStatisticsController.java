package br.com.ibicos.ibicos.api.controller;

import br.com.ibicos.ibicos.domain.entity.CustomerStatistics;
import br.com.ibicos.ibicos.domain.service.CustomerStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/customers-statistics")
@AllArgsConstructor
public class CustomerStatisticsController {

    private final CustomerStatisticsService customerStatiticsService;

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerStatistics(@PathVariable Integer customerId) {
        CustomerStatistics customerStatistics = customerStatiticsService
                .getCustomerStatistics(customerId);

        return ResponseEntity.ok()
                .body(Map.of("customer-statistics", customerStatistics));
    }
}

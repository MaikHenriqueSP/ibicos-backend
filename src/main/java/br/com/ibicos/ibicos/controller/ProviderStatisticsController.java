package br.com.ibicos.ibicos.controller;

import br.com.ibicos.ibicos.dto.ProviderSelfStatisticsDTO;
import br.com.ibicos.ibicos.entity.Statistics;
import br.com.ibicos.ibicos.service.ProviderStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/providers-statistics")
public class ProviderStatisticsController {

    private final ProviderStatisticsService providerStatisticsService;

    @GetMapping("/{providerId}")
    public ResponseEntity<?> getProviderSelfStatistics(@PathVariable Integer providerId) {
        ProviderSelfStatisticsDTO providerSelfStatistics = providerStatisticsService.getProviderSelfStatisticsById(providerId);
        return ResponseEntity.ok().body(Map.of("provider_statistics", providerSelfStatistics));
    }
}

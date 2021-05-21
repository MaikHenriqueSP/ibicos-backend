package br.com.ibicos.ibicos.domain.service;

import br.com.ibicos.ibicos.domain.entity.CustomerStatistics;
import br.com.ibicos.ibicos.domain.exception.ResourceNotFoundException;
import br.com.ibicos.ibicos.domain.repository.CustomerStatisticsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerStatisticsService {
    private final CustomerStatisticsRepository customerStatisticsRepository;

    public CustomerStatistics getCustomerStatistics(Integer customerId) {
        Optional<CustomerStatistics> customerStatisticsOptional = customerStatisticsRepository.findByUserId(customerId);

        if (customerStatisticsOptional.isEmpty()) {
            throw new ResourceNotFoundException(String.format("There is no user with id: %s customerId", customerId));
        }

        return customerStatisticsOptional.get();
    }
}

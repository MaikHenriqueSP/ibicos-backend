package br.com.ibicos.ibicos.service;

import br.com.ibicos.ibicos.domain.entity.CustomerStatistics;
import br.com.ibicos.ibicos.domain.exception.ResourceNotFoundException;
import br.com.ibicos.ibicos.domain.repository.CustomerStatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerStatisticsService {
    private CustomerStatisticsRepository customerStatisticsRepository;

    public CustomerStatisticsService(CustomerStatisticsRepository customerStatisticsRepository) {
        this.customerStatisticsRepository = customerStatisticsRepository;
    }

    public CustomerStatistics getCustomerStatistics(Integer customerId) {
        Optional<CustomerStatistics> customerStatisticsOptional = customerStatisticsRepository.findByUserId(customerId);

        if (customerStatisticsOptional.isEmpty()) {
            throw new ResourceNotFoundException(String.format("There is no user with id: % customerId", customerId));
        }

        return customerStatisticsOptional.get();
//        return null;
    }
}

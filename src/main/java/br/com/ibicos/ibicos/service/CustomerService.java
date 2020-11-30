package br.com.ibicos.ibicos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import br.com.ibicos.ibicos.dto.CustomerEmailToProviderDTO;
import br.com.ibicos.ibicos.email.EmailService;
import br.com.ibicos.ibicos.entity.Statistics;
import br.com.ibicos.ibicos.repository.StatisticsRepository;

@Service
public class CustomerService {
	@Autowired
	private StatisticsRepository statisticsRepository;
	
	@Autowired
	private EmailService emailService;
	
	public Statistics showCustomerStatistics(Integer customerId) {
		Optional<Statistics> optionalStatistic = statisticsRepository
				.findCustomerStatistic(customerId);
		
		if(optionalStatistic.isEmpty()) {
			throw new ResourceAccessException("There's no customer statistic with the provided ID");
		}
		
		return optionalStatistic.get();
	}

	public void sendEmailToProvider(CustomerEmailToProviderDTO customerEmailToProviderDTO) {
		emailService.sendEmailToProvider(customerEmailToProviderDTO);

	}
	
}

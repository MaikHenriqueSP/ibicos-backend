package br.com.ibicos.ibicos.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import br.com.ibicos.ibicos.entity.User;
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

	@Autowired
	private UserService userService;

	@Autowired
	private EvaluateService evaluateService;
	
	public Statistics showCustomerStatistics(Integer customerId) {
		Optional<Statistics> optionalStatistic = statisticsRepository
				.findCustomerStatistic(customerId);
		
		if(optionalStatistic.isEmpty()) {
			throw new ResourceAccessException("There's no customer statistic with the provided ID");
		}
		
		return optionalStatistic.get();
	}

	public void sendEmailToProvider(CustomerEmailToProviderDTO customerEmailToProviderDTO) {
		User customer =  userService.findUserById(customerEmailToProviderDTO.getCustomerId());
		User provider =  userService.findUserById(customerEmailToProviderDTO.getProviderId());

		Map<String, Object> contextEmailMapVariables = createEmailContextVariablesMap(customer, provider, customerEmailToProviderDTO);

		emailService.sendEmailToProvider(provider, contextEmailMapVariables);
		evaluateService.registerPendingEvaluation(customerEmailToProviderDTO, customer, provider);
	}

	private Map<String, Object> createEmailContextVariablesMap(User customer, User provider, CustomerEmailToProviderDTO customerEmailToProviderDTO) {
		Map<String, Object> contextVariablesMap = new HashMap<>();
		contextVariablesMap.put("customerName", customer.getPerson().getNamePerson());
		contextVariablesMap.put("providerName", provider.getPerson().getNamePerson());
		contextVariablesMap.put("customerEmailAddress", customer.getEmail());
		contextVariablesMap.put("providerEmailAddress", provider.getEmail());
		contextVariablesMap.put("message", customerEmailToProviderDTO.getMessage());
		return contextVariablesMap;
	}


}

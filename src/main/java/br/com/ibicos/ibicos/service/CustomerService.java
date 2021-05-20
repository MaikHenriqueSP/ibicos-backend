package br.com.ibicos.ibicos.service;

import br.com.ibicos.ibicos.dto.CustomerEmailToProviderDTO;
import br.com.ibicos.ibicos.dto.CustomerSelfStatisticsDTO;
import br.com.ibicos.ibicos.dto.EmailDataDTO;
import br.com.ibicos.ibicos.email.EmailService;
import br.com.ibicos.ibicos.domain.entity.Statistics;
import br.com.ibicos.ibicos.domain.entity.User;
import br.com.ibicos.ibicos.repository.StatisticsRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerService {

	private final StatisticsRepository statisticsRepository;
	private final EmailService emailService;
	private final UserService userService;
	private final EvaluateService evaluateService;

	public CustomerService(StatisticsRepository statisticsRepository, EmailService emailService, UserService userService, EvaluateService evaluateService) {
		this.statisticsRepository = statisticsRepository;
		this.emailService = emailService;
		this.userService = userService;
		this.evaluateService = evaluateService;
	}

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

		EmailDataDTO emailDataDTO = EmailDataDTO.builder().
				subject("iBicos - Mensagem do cliente")
				.from("ibicos.classificados@gmail.com")
				.to(provider.getEmail())
				.templateName("email-message-from-customer-to-provider")
				.build();

		emailService.sendEmail(emailDataDTO, contextEmailMapVariables);
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


	public CustomerSelfStatisticsDTO getCustomerSelfStatisticsById(Integer customerId) {
		Optional<CustomerSelfStatisticsDTO> selfCustomerStatistics = statisticsRepository.findSelfCustomerStatisticsById(customerId);
		return selfCustomerStatistics.orElseThrow(() -> new RuntimeException("There is not customer with the given id"));
	}
}

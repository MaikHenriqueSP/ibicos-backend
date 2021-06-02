package br.com.ibicos.ibicos.domain.service;

import br.com.ibicos.ibicos.api.dto.CustomerEmailToProviderDTO;
import br.com.ibicos.ibicos.api.dto.EmailDataDTO;
import br.com.ibicos.ibicos.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class CustomerService {

	private final EmailService emailService;
	private final UserService userService;
	private final EvaluateService evaluateService;

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

}

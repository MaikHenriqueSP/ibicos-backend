package br.com.ibicos.ibicos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.com.ibicos.ibicos.dto.CustomerEmailToProviderDTO;
import br.com.ibicos.ibicos.entity.ServiceCategory;
import br.com.ibicos.ibicos.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ibicos.ibicos.entity.Evaluate;
import br.com.ibicos.ibicos.entity.Statistics;
import br.com.ibicos.ibicos.exception.ResourceNotFoundException;
import br.com.ibicos.ibicos.repository.EvaluateRepository;

@Service
public class EvaluateService {

	@Autowired
	private EvaluateRepository evaluateRepository;

	@Autowired
	private StatisticsService statisticsService;

	private Evaluate findEvaluateStatisticsByIdEvaluateOrElseThrowRuntimeException(Integer idEvaluate) {
		Optional<Evaluate> evaluateOptional = evaluateRepository.findById(idEvaluate);

		if (evaluateOptional.isEmpty()) {
			throw new ResourceNotFoundException("No evaluate object found with the given id");
		}

		return evaluateOptional.get();
	}

	private Statistics findProviderStatisticsByIdProviderAndIdServiceCategoryOrElseThrowRuntimeException(
			Integer idProvider, Integer idCategory) {
		Optional<Statistics> optionalStatistics = statisticsService
				.findProviderStatisticsByIdProviderAndIdServiceCategory(idProvider, idCategory);

		if (optionalStatistics.isEmpty()) {
			throw new RuntimeException("There is not such provider statistics for the given parameters");
		}

		return optionalStatistics.get();
	}

	private Statistics findCustomerStatisticsOrElseThrowRuntimeException(Integer idClient) {
		Optional<Statistics> optionalStatistics = statisticsService.findCustomerStatistic(idClient);

		if (optionalStatistics.isEmpty()) {
			throw new RuntimeException("There is not such customer statistics for the given parameters");
		}

		return optionalStatistics.get();
	}

	public void evaluateProviderJobConfirmation(Integer idEvaluate) {
		Evaluate evaluate = findEvaluateStatisticsByIdEvaluateOrElseThrowRuntimeException(idEvaluate);

		Integer idServiceCategory = evaluate.getServiceCategory().getId();
		Integer idProvider = evaluate.getProvider().getId();
		Integer idClient = evaluate.getClient().getId();

		Statistics customerStatistics = findCustomerStatisticsOrElseThrowRuntimeException(idClient);
		Statistics providerStatistics = findProviderStatisticsByIdProviderAndIdServiceCategoryOrElseThrowRuntimeException(
				idProvider, idServiceCategory);

		evaluate.setHired(true);
		providerStatistics.setHiredServicesCounter(providerStatistics.getHiredServicesCounter() + 1);
		customerStatistics.setHiredServicesCounter(customerStatistics.getHiredServicesCounter() + 1);

		evaluateRepository.save(evaluate);
		statisticsService.save(providerStatistics);
		statisticsService.save(customerStatistics);
	}

	public void evaluateProvider(Integer idEvaluate, Float evaluation) {
		Evaluate evaluate = findEvaluateStatisticsByIdEvaluateOrElseThrowRuntimeException(idEvaluate);

		Integer idServiceCategory = evaluate.getServiceCategory().getId();
		Integer idProvider = evaluate.getProvider().getId();

		Statistics providerStatistics = findProviderStatisticsByIdProviderAndIdServiceCategoryOrElseThrowRuntimeException(
				idProvider, idServiceCategory);

		if (evaluate.isProviderEvaluated()) {
			throw new RuntimeException("The provider was already evaluated");
		}

		Integer evaluationCounter = providerStatistics.getEvaluationsCounter();
		Float newProviderEvaluation = ((providerStatistics.getEvaluation() * evaluationCounter) + evaluation)
				/ (evaluationCounter + 1);

		providerStatistics.setEvaluation(newProviderEvaluation);
		providerStatistics.setEvaluationsCounter(evaluationCounter + 1);

		evaluate.setProviderEvaluated(true);
		statisticsService.save(providerStatistics);
		evaluateRepository.save(evaluate);
	}

	public void registerPendingEvaluation(CustomerEmailToProviderDTO customerEmailToProviderDTO) {
		User customer = customerEmailToProviderDTO.getCustomer();
		User provider = customerEmailToProviderDTO.getProvider();

		ServiceCategory serviceCategory = customerEmailToProviderDTO.getServiceCategory();

		Evaluate pendingEvaluation = Evaluate.builder()
				.client(customer)
				.provider(provider)
				.messageDate(LocalDate.now())
				.serviceCategory(serviceCategory)
				.build();

		evaluateRepository.save(pendingEvaluation);
	}

    public List<Evaluate> listEvaluationsByCustomerId(Integer customerId) {
		return evaluateRepository.findByCustomerId(customerId);
    }

	public void deleteEvaluationById(Integer idEvaluate) {
		evaluateRepository.deleteById(idEvaluate);
	}
}

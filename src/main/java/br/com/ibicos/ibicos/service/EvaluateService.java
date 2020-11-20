package br.com.ibicos.ibicos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ibicos.ibicos.entity.Evaluate;
import br.com.ibicos.ibicos.entity.Statistics;
import br.com.ibicos.ibicos.exception.ResourceNotFoundException;
import br.com.ibicos.ibicos.repository.EvaluateRepository;
import br.com.ibicos.ibicos.repository.StatisticsRepository;

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

		System.out.println(idProvider);
		System.out.println(idClient);
		System.out.println(idServiceCategory);
		Statistics customerStatistics = findCustomerStatisticsOrElseThrowRuntimeException(idClient);
		System.out.println(customerStatistics);
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
}

package br.com.ibicos.ibicos.service;

import br.com.ibicos.ibicos.dto.CustomerEmailToProviderDTO;
import br.com.ibicos.ibicos.dto.IncrementViewsRequestDTO;
import br.com.ibicos.ibicos.dto.ServiceCategoryDTO;
import br.com.ibicos.ibicos.dto.UserDTO;
import br.com.ibicos.ibicos.domain.entity.*;
import br.com.ibicos.ibicos.exception.ResourceNotFoundException;
import br.com.ibicos.ibicos.repository.EvaluateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluateService {

	private final EvaluateRepository evaluateRepository;
	private final StatisticsService statisticsService;
	private final ServiceCategoryService serviceCategoryService;
	private final ProviderStatisticsService providerStatisticsService;

	public EvaluateService(EvaluateRepository evaluateRepository, StatisticsService statisticsService, ServiceCategoryService serviceCategoryService, ProviderStatisticsService providerStatisticsService) {
		this.evaluateRepository = evaluateRepository;
		this.statisticsService = statisticsService;
		this.serviceCategoryService = serviceCategoryService;
		this.providerStatisticsService = providerStatisticsService;
	}

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
			throw new RuntimeException("There is no such provider statistics for the given parameters");
		}

		return optionalStatistics.get();
	}

	private Statistics findCustomerStatisticsOrElseThrowRuntimeException(Integer idClient) {
		Optional<Statistics> optionalStatistics = statisticsService.findCustomerStatistic(idClient);

		if (optionalStatistics.isEmpty()) {
			throw new RuntimeException("There is no such customer statistics for the given parameters");
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


		Float newProviderEvaluation = calculateNewEvaluation(evaluation, providerStatistics);

		providerStatistics.setEvaluation(newProviderEvaluation);
		providerStatistics.setEvaluationsCounter(providerStatistics.getEvaluationsCounter() + 1);

		evaluate.setProviderEvaluated(true);
		statisticsService.save(providerStatistics);
		updateEvaluate(evaluate);
	}

	private Float calculateNewEvaluation(Float evaluation, Statistics statistics) {
		Integer evaluationCounter = statistics.getEvaluationsCounter();
		return ((statistics.getEvaluation() * evaluationCounter) + evaluation)
				/ (evaluationCounter + 1);
	}

	public void registerPendingEvaluation(CustomerEmailToProviderDTO customerEmailToProviderDTO, User customer, User provider) {
		serviceCategoryService.getServiceCategoryByServiceCategoryDTO(customerEmailToProviderDTO.getServiceCategoryDTO());

		ServiceCategory serviceCategory = serviceCategoryService
				.getServiceCategoryByServiceCategoryDTO(customerEmailToProviderDTO.getServiceCategoryDTO());

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

	public List<Evaluate> listEvaluationsByProviderId(Integer providerId) {
		return evaluateRepository.findByProviderIdNotEvaluatedAndHired(providerId);
	}

    public void evaluateCustomer(Integer idEvaluate, Float evaluation) {
		Evaluate evaluate = findEvaluateStatisticsByIdEvaluateOrElseThrowRuntimeException(idEvaluate);
		Integer customerId = evaluate.getClient().getId();

		Statistics customerStatistics = findCustomerStatisticsOrElseThrowRuntimeException(customerId);

		Float newCustomerEvaluation = calculateNewEvaluation(evaluation, customerStatistics);
		customerStatistics.setEvaluationsCounter(customerStatistics.getEvaluationsCounter() + 1);
		customerStatistics.setEvaluation(newCustomerEvaluation);
		evaluate.setCustomerEvaluated(true);
		updateEvaluate(evaluate);
		statisticsService.save(customerStatistics);
    }

    private void updateEvaluate(Evaluate evaluate) {
		if (isEvaluateDeletable(evaluate)) {
			Integer idEvaluate = evaluate.getIdEvaluate();
			evaluateRepository.deleteById(idEvaluate);
		} else {
			evaluateRepository.save(evaluate);
		}
	}

    private boolean isEvaluateDeletable(Evaluate evaluate) {
		return evaluate.isProviderEvaluated() && evaluate.isCustomerEvaluated();
	}

	public void incrementProviderVisualizations(IncrementViewsRequestDTO incrementViewsRequestDTO) {
		UserDTO providerUser = incrementViewsRequestDTO.getProviderUser();
		ServiceCategoryDTO serviceCategoryDTO = incrementViewsRequestDTO.getServiceCategory();

		ServiceCategory serviceCategory = serviceCategoryService
				.getServiceCategoryByServiceCategoryDTO(serviceCategoryDTO);

		Optional<ProviderStatistics> providerStatisticsOptional = providerStatisticsService
				.getProviderStatisticsOptional(providerUser.getId(), serviceCategory.getId());

		if (providerStatisticsOptional.isPresent()) {
			ProviderStatistics providerStatistics = providerStatisticsOptional.get();
			providerStatistics.setVisualizations(providerStatistics.getVisualizations() + 1);
			providerStatisticsService.save(providerStatistics);
		}
	}
}

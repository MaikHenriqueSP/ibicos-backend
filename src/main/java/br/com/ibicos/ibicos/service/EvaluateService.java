package br.com.ibicos.ibicos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ibicos.ibicos.dto.EvaluationDTO;
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
	private StatisticsRepository statisticsRepository;

	private Evaluate checkIfEvaluateExists(Integer idEvaluate) {
		Optional<Evaluate> evaluateOptional = evaluateRepository.findById(idEvaluate);

		if (evaluateOptional.isEmpty()) {
			throw new ResourceNotFoundException("No evaluate object found with the given id");
		}

		return evaluateOptional.get();
	}
	
	private Statistics checkIfProviderStatisticsExists(Integer idProvider, Integer idCategory) {
		Optional<Statistics> optionalStatistics = statisticsRepository.findProviderStatisticByProviderIdAndCategoryId(
				idProvider, idCategory);
		
		if (optionalStatistics.isEmpty()) {
			throw new RuntimeException("There is not such provider statistics for the given parameters");
		}
		
		return optionalStatistics.get();			
	}
	
//	TODO incremente hired services counter for customer and delegate the statistics works to 
	//statisticsService
	public void evaluateProviderJobConfirmation(Integer idEvaluate, Integer idServiceCategory) {
		Evaluate evaluate = checkIfEvaluateExists(idEvaluate);
		
		Integer idProvider = evaluate.getProvider().getId();
		Integer idClient = evaluate.getClient().getId();
		
		Statistics providerStatistics = checkIfProviderStatisticsExists(idProvider, idServiceCategory);
		
		evaluate.setHired(true);
		evaluateRepository.save(evaluate);
		
		providerStatistics.setHiredServicesCounter(providerStatistics.getHiredServicesCounter() + 1);
		statisticsRepository.save(providerStatistics);
	}

	public void evaluateProvider(EvaluationDTO evaluationDTO) {
		Evaluate evaluate = checkIfEvaluateExists(evaluationDTO.getIdEvaluate());
		Statistics providerStatistics = checkIfProviderStatisticsExists(evaluationDTO.getIdProvider(),
				evaluationDTO.getIdCategory());

		if (evaluate.isProviderEvaluated()) {
			throw new RuntimeException("The provider was already evaluated");
		}
		
		Integer evaluationCounter = providerStatistics.getEvaluationsCounter();		
		Float newProviderEvaluation = ((providerStatistics.getEvaluation() * evaluationCounter) + evaluationDTO.getEvaluation()) 
				/ (evaluationCounter + 1);
				
		providerStatistics.setEvaluation(newProviderEvaluation);
		providerStatistics.setEvaluationsCounter(evaluationCounter + 1);		
					
		evaluate.setProviderEvaluated(true);
		
		statisticsRepository.save(providerStatistics);
		evaluateRepository.save(evaluate);
	}
}

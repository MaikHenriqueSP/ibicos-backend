package br.com.ibicos.ibicos.domain.service;

import br.com.ibicos.ibicos.api.dto.ProviderSelfStatisticsDTO;
import br.com.ibicos.ibicos.domain.entity.ProviderStatistics;
import br.com.ibicos.ibicos.domain.entity.ServiceCategory;
import br.com.ibicos.ibicos.domain.entity.Statistics;
import br.com.ibicos.ibicos.domain.entity.User;
import br.com.ibicos.ibicos.domain.exception.ResourceNotFoundException;
import br.com.ibicos.ibicos.domain.repository.ProviderStatisticsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderStatisticsService {

	private final ProviderStatisticsRepository providerStatisticsRepository;

	public ProviderStatisticsService(ProviderStatisticsRepository providerStatisticsRepository) {
		this.providerStatisticsRepository = providerStatisticsRepository;
	}

	public List<ProviderStatistics> listProviderStatistics() {
		return providerStatisticsRepository.findAll();
	}
	
//	public Page<ProviderStatistics> listProviderStatisticsByStatisticsId(Integer statisticsId, Pageable pageable) {
//		return providerStatisticsRepository.findByStatisticsId(statisticsId, pageable);
//	}

	@Transactional(rollbackFor = { RuntimeException.class})
	public ProviderStatistics createProviderStatisticsIfItNotExists(User adCreator, ServiceCategory adCategory) {
		Optional<ProviderStatistics> providerStatisticsOptional = getProviderStatisticsOptional(adCreator, adCategory);

		if(providerStatisticsOptional.isEmpty()) {
			Statistics statistics = Statistics.builder()
					.evaluation(0f)
					.evaluationsCounter(0)
					.hiredServicesCounter(0)
					.messagesCounter(0)
					.user(adCreator).build();

			ProviderStatistics providerStatistics = ProviderStatistics.builder()
					.evaluation(statistics.getEvaluation())
					.evaluationsCounter(statistics.getEvaluationsCounter())
					.hiredServicesCounter(statistics.getHiredServicesCounter())
					.messagesCounter(statistics.getMessagesCounter())
					.user(adCreator)
					.visualizations(0)
					.category(adCategory).build();
			
			return providerStatisticsRepository.save(providerStatistics);
		}
		
		return providerStatisticsOptional.get();		
	}

	private Optional<ProviderStatistics> getProviderStatisticsOptional(User user, ServiceCategory serviceCategory) {
		return providerStatisticsRepository.findByServiceCategoryIdAndUserId(serviceCategory.getId(), user.getId());
	}

	public Optional<ProviderStatistics> getProviderStatisticsOptional(Integer userId, Integer serviceCategoryId) {
		return providerStatisticsRepository.findByServiceCategoryIdAndUserId(serviceCategoryId, userId);
	}

	public void save(ProviderStatistics providerStatistics) {
		providerStatisticsRepository.save(providerStatistics);
	}


	public ProviderSelfStatisticsDTO getProviderSelfStatisticsById(Integer providerId) {
		Optional<ProviderSelfStatisticsDTO> providerSelfStatistics = providerStatisticsRepository.findSelfStatisticsById(providerId);

		return providerSelfStatistics.orElseThrow(() -> new ResourceNotFoundException("There is not provider with the given id"));
	}
}

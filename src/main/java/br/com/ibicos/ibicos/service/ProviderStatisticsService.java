package br.com.ibicos.ibicos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ibicos.ibicos.entity.ProviderStatistics;
import br.com.ibicos.ibicos.entity.ServiceCategory;
import br.com.ibicos.ibicos.entity.Statistics;
import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.repository.ProviderStatisticsRepository;

@Service
public class ProviderStatisticsService {

	@Autowired
	private ProviderStatisticsRepository providerStatisticsRepository;
	
	public List<ProviderStatistics> listProviderStatistics() {
		return providerStatisticsRepository.findAll();
	}
	
	public Page<ProviderStatistics> listProviderStatisticsByStatisticsId(Integer statisticsId, Pageable pageable) {
		return providerStatisticsRepository.findByStatisticsId(statisticsId, pageable);
	}

	@Transactional(rollbackFor = { RuntimeException.class})
	public ProviderStatistics createProviderStatisticsIfItNotExists(User adCreator, ServiceCategory adCategory) {
		Optional<ProviderStatistics> providerStatisticsOptional =   
				providerStatisticsRepository.findByServiceCategoryIdAndUserId(adCategory.getId(), adCreator.getId());
		
		if(providerStatisticsOptional.isEmpty()) {
			Statistics statistics = Statistics.builder()
					.evaluation(0f)
					.evaluationsCounter(0)
					.hiredServicesCounter(0)
					.messagesCounter(0)
					.viewsCounter(0)
					.user(adCreator).build();

			ProviderStatistics providerStatistics = ProviderStatistics.builder().
					statistics(statistics)
					.visualizations(0)
					.category(adCategory).build();
			
			return providerStatisticsRepository.save(providerStatistics);
		}
		
		return providerStatisticsOptional.get();		
	}
	
	
}

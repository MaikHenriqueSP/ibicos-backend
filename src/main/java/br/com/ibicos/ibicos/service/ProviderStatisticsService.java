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
			ProviderStatistics providerStatistics = new ProviderStatistics();
			Statistics statistics = new Statistics();			
			
			statistics.setEvaluation(0f);
			statistics.setEvaluationsCounter(0);
			statistics.setHiredServicesCounter(0);
			statistics.setMessagesCounter(0);
			statistics.setViewsCounter(0);
			statistics.setUser(adCreator);
			
			providerStatistics.setStatistics(statistics);
			providerStatistics.setVisualizations(0);
			providerStatistics.setCategory(adCategory);
			
			return providerStatisticsRepository.save(providerStatistics);
		}
		
		return providerStatisticsOptional.get();		
	}
	
	
}

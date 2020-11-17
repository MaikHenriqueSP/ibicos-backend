package br.com.ibicos.ibicos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ibicos.ibicos.entity.ProviderStatistics;
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
}

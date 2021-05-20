package br.com.ibicos.ibicos.domain.service;

import br.com.ibicos.ibicos.domain.entity.Statistics;
import br.com.ibicos.ibicos.domain.entity.User;
import br.com.ibicos.ibicos.domain.repository.StatisticsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatisticsService {

	private final StatisticsRepository statisticsRepository;

	public StatisticsService(StatisticsRepository statisticsRepository) {
		this.statisticsRepository = statisticsRepository;
	}

	public List<Statistics> listStatistics() {
		return statisticsRepository.findAll();
	}
	
	public Page<Statistics> listStatisticsByUserId(Integer userId, Pageable pageable) {
		return statisticsRepository.findByUserId(userId, pageable);
	}
	
	public Optional<Statistics> showStatistics(Integer id) {
		return statisticsRepository.findById(id);
	}
	
	
	public Optional<Statistics> findProviderStatisticsByIdProviderAndIdServiceCategory(Integer idProvider, 
			Integer idCategory) {
		return statisticsRepository.findProviderStatisticByProviderIdAndCategoryId(
				idProvider, idCategory);
	}
	
	public Optional<Statistics> findCustomerStatistic(Integer idCustomer) {
		return statisticsRepository.findCustomerStatistic(idCustomer);
	}
	
	public Statistics save(Statistics statistics) {
		return statisticsRepository.save(statistics);
	}

	public Statistics createCustomerStatistics(User user) {
		Statistics customerStatistics = Statistics.builder().user(user).evaluation(0.0f)
				.evaluationsCounter(0)
				.hiredServicesCounter(0)
				.messagesCounter(0)
				.build();
		return statisticsRepository.save(customerStatistics);
	}
}

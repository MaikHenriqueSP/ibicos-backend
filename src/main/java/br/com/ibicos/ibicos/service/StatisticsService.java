package br.com.ibicos.ibicos.service;

import java.util.List;
import java.util.Optional;

import br.com.ibicos.ibicos.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ibicos.ibicos.entity.Statistics;
import br.com.ibicos.ibicos.repository.StatisticsRepository;

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
				.viewsCounter(0).build();
		return statisticsRepository.save(customerStatistics);
	}
}

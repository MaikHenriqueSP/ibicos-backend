package br.com.ibicos.ibicos.service;

import br.com.ibicos.ibicos.dto.ProviderSelfStatisticsDTO;
import br.com.ibicos.ibicos.domain.entity.User;
import br.com.ibicos.ibicos.repository.ProviderStatisticsRepository;
import br.com.ibicos.ibicos.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

	private final UserRepository userRepository;
	private final ProviderStatisticsRepository providerStatisticsRepository;

	public ProviderService(UserRepository userRepository, ProviderStatisticsRepository providerStatisticsRepository) {
		this.userRepository = userRepository;
		this.providerStatisticsRepository = providerStatisticsRepository;
	}

	public List<User> listProviders() {
		return userRepository.findAll();
	}
	
	public Optional<User> showProvider(Integer providerId) {
		return userRepository.findById(providerId);
	}

    public ProviderSelfStatisticsDTO getProviderSelfStatisticsById(Integer providerId) {
//		Optional<ProviderSelfStatisticsDTO> providerSelfStatistics = providerStatisticsRepository.findSelfStatisticsById(providerId);

//		return providerSelfStatistics.orElseThrow(() -> new RuntimeException("There is not provider with the given id"));
		return null;
	}
}

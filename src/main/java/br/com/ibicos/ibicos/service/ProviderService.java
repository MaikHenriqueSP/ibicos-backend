package br.com.ibicos.ibicos.service;

import java.util.List;
import java.util.Optional;

import br.com.ibicos.ibicos.dto.ProviderSelfStatisticsDTO;
import br.com.ibicos.ibicos.entity.ProviderStatistics;
import br.com.ibicos.ibicos.repository.ProviderStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.repository.UserRepository;

@Service
public class ProviderService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProviderStatisticsRepository providerStatisticsRepository;
	
	public List<User> listProviders() {
		return userRepository.findAll();
	}
	
	public Optional<User> showProvider(Integer providerId) {
		return userRepository.findById(providerId);
	}

    public ProviderSelfStatisticsDTO getProviderSelfStatisticsById(Integer providerId) {
		Optional<ProviderSelfStatisticsDTO> providerSelfStatistics = providerStatisticsRepository.findSelfStatisticsById(providerId);

		if (providerSelfStatistics.isEmpty()) {
			throw new RuntimeException("There is not provider with the given id");
		}

		return providerSelfStatistics.get();
	}
}

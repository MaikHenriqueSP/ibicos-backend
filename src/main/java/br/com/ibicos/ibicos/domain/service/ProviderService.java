package br.com.ibicos.ibicos.domain.service;

import br.com.ibicos.ibicos.api.dto.ProviderSelfStatisticsDTO;
import br.com.ibicos.ibicos.domain.entity.User;
import br.com.ibicos.ibicos.domain.repository.ProviderStatisticsRepository;
import br.com.ibicos.ibicos.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProviderService {

	private final UserRepository userRepository;
	private final ProviderStatisticsRepository providerStatisticsRepository;

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

package br.com.ibicos.ibicos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.repository.UserRepository;

@Service
public class ProviderService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> listProviders() {
		return userRepository.findAll();
	}
	
	public Optional<User> showProvider(Integer providerId) {
		return userRepository.findById(providerId);
	}
}

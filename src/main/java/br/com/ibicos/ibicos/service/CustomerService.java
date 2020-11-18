package br.com.ibicos.ibicos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import br.com.ibicos.ibicos.entity.Person;
import br.com.ibicos.ibicos.entity.Statistics;
import br.com.ibicos.ibicos.exception.InvalidInsertionObjectException;
import br.com.ibicos.ibicos.repository.PersonRepository;
import br.com.ibicos.ibicos.repository.StatisticsRepository;

@Service
public class CustomerService {
	@Autowired
	private StatisticsRepository statisticsRepository;
	
	public Statistics listCustomerStatistics(Integer id) {
		return null;
	}
}

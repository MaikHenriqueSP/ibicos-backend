package br.com.ibicos.ibicos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ibicos.ibicos.entity.ServiceCategory;
import br.com.ibicos.ibicos.repository.ServiceCategoryRepository;

@Service
public class ServiceCategoryService {
	@Autowired
	private ServiceCategoryRepository serviceCategoryRepository;
	
	public List<ServiceCategory> listAllServicesCategories() {
		return serviceCategoryRepository.findAll();
	}

}

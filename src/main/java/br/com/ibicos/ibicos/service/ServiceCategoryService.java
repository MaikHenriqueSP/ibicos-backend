package br.com.ibicos.ibicos.service;

import java.util.List;

import br.com.ibicos.ibicos.dto.ServiceCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ibicos.ibicos.entity.ServiceCategory;
import br.com.ibicos.ibicos.repository.ServiceCategoryRepository;

@Service
public class ServiceCategoryService {

	private final ServiceCategoryRepository serviceCategoryRepository;

	public ServiceCategoryService(ServiceCategoryRepository serviceCategoryRepository) {
		this.serviceCategoryRepository = serviceCategoryRepository;
	}

	public List<ServiceCategory> listAllServicesCategories() {
		return serviceCategoryRepository.findAll();
	}

	public ServiceCategory getServiceCategoryByServiceCategoryDTO(ServiceCategoryDTO serviceCategoryDTO) {
		return serviceCategoryRepository.findByCategoryName(serviceCategoryDTO.getCategoryName());
	}

}

package br.com.ibicos.ibicos.domain.service;

import br.com.ibicos.ibicos.api.dto.ServiceCategoryDTO;
import br.com.ibicos.ibicos.domain.entity.ServiceCategory;
import br.com.ibicos.ibicos.domain.repository.ServiceCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

package br.com.ibicos.ibicos.domain.service;

import br.com.ibicos.ibicos.api.dto.ServiceCategoryDTO;
import br.com.ibicos.ibicos.domain.entity.ServiceCategory;
import br.com.ibicos.ibicos.domain.repository.ServiceCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceCategoryService {

	private final ServiceCategoryRepository serviceCategoryRepository;

	public List<ServiceCategory> listAllServicesCategories() {
		return serviceCategoryRepository.findAll();
	}

	public ServiceCategory getServiceCategoryByServiceCategoryDTO(ServiceCategoryDTO serviceCategoryDTO) {
		return serviceCategoryRepository.findByCategoryName(serviceCategoryDTO.getCategoryName());
	}

}

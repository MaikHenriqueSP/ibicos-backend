package br.com.ibicos.ibicos.api.controller;

import br.com.ibicos.ibicos.domain.entity.ServiceCategory;
import br.com.ibicos.ibicos.service.ServiceCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service-categories")
public class ServiceCategoryController {
	
	private final ServiceCategoryService serviceCategoryService;

	public ServiceCategoryController(ServiceCategoryService serviceCategoryService) {
		this.serviceCategoryService = serviceCategoryService;
	}

	@GetMapping
	public ResponseEntity<?> listAllServicesCategories() {
		List<ServiceCategory> servicesCategories = serviceCategoryService.listAllServicesCategories();
		return ResponseEntity.status(HttpStatus.OK).body(servicesCategories);
	} 
	
	

}

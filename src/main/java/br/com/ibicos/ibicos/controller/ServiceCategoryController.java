package br.com.ibicos.ibicos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibicos.ibicos.entity.ServiceCategory;
import br.com.ibicos.ibicos.service.ServiceCategoryService;

@RestController
@RequestMapping("/v1/service-category")
public class ServiceCategoryController {

	@Autowired
	private ServiceCategoryService serviceCategoryService;
	
	@GetMapping
	public ResponseEntity<?> listAllServicesCategories() {
		List<ServiceCategory> servicesCategories = serviceCategoryService.listAllServicesCategories();
		return ResponseEntity.status(HttpStatus.OK).body(servicesCategories);
	} 
}
package br.com.ibicos.ibicos.mapper;

import br.com.ibicos.ibicos.dto.ServiceCategoryDTO;
import br.com.ibicos.ibicos.entity.ServiceCategory;
import org.mapstruct.Mapper;

@Mapper
public interface ServiceCategoryMapper {
    ServiceCategoryDTO serviceCategoryToServiceCategoryDTO(ServiceCategory serviceCategory);
}

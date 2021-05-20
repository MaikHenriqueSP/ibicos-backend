package br.com.ibicos.ibicos.repository;

import br.com.ibicos.ibicos.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Integer> {

    ServiceCategory findByCategoryName(String categoryName);
}

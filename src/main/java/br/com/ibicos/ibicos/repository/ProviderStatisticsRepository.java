package br.com.ibicos.ibicos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ibicos.ibicos.entity.ProviderStatistics;

@Repository
public interface ProviderStatisticsRepository extends JpaRepository<ProviderStatistics, Integer>{

}

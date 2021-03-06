package br.com.ibicos.ibicos.domain.repository;

import br.com.ibicos.ibicos.domain.entity.AdCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdCityRepository extends JpaRepository<AdCity, Integer> {

}

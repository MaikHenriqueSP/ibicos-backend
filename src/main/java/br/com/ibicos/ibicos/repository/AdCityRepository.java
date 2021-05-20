package br.com.ibicos.ibicos.repository;

import br.com.ibicos.ibicos.entity.AdCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdCityRepository extends JpaRepository<AdCity, Integer> {

}

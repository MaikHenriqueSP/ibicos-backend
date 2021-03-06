package br.com.ibicos.ibicos.domain.repository;

import br.com.ibicos.ibicos.domain.entity.AdRegionArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRegionAreaRepository extends JpaRepository<AdRegionArea, Integer> {

}

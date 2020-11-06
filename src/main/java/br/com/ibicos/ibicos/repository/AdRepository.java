package br.com.ibicos.ibicos.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.ibicos.ibicos.entity.Ad;

@Repository
public interface AdRepository extends PagingAndSortingRepository<Ad, Integer>{

}

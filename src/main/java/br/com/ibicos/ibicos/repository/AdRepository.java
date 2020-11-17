package br.com.ibicos.ibicos.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.ibicos.ibicos.entity.Ad;

@Repository
public interface AdRepository extends PagingAndSortingRepository<Ad, Integer>{
	Page<Ad> findByUserId(Integer userId, Pageable pageable);
	Optional<Ad> findByIdAndUserId(Integer adId, Integer userId);
}

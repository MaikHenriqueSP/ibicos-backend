package br.com.ibicos.ibicos.domain.repository;

import br.com.ibicos.ibicos.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}

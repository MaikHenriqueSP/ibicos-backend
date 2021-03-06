package br.com.ibicos.ibicos.domain.service;

import br.com.ibicos.ibicos.domain.entity.Address;
import br.com.ibicos.ibicos.domain.entity.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonService {
    private final AddressService addressService;

    public void updatePerson(Person oldPerson, Person newPerson) {
        oldPerson.setCnpj(newPerson.getCnpj());

        Address oldAddress = oldPerson.getAddress();
        Address newAddress = newPerson.getAddress();
    }
}

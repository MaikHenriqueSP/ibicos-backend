package br.com.ibicos.ibicos.domain.service;

import br.com.ibicos.ibicos.domain.entity.Address;
import br.com.ibicos.ibicos.domain.entity.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final AddressService addressService;

    public PersonService(AddressService addressService) {
        this.addressService = addressService;
    }

    public void updatePerson(Person oldPerson, Person newPerson) {
        oldPerson.setCnpj(newPerson.getCnpj());

        Address oldAddress = oldPerson.getAddress();
        Address newAddress = newPerson.getAddress();
    }
}

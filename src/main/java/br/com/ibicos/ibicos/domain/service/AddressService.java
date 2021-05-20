package br.com.ibicos.ibicos.domain.service;

import br.com.ibicos.ibicos.domain.entity.Address;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    public void updateAddress(Address oldAddress, Address newAddress) {
        oldAddress.setCep(newAddress.getCep());
        oldAddress.setCity(newAddress.getCity());
        oldAddress.setComplement(newAddress.getComplement());
        oldAddress.setNeighborhood(newAddress.getNeighborhood());
        oldAddress.setNumberAddress(newAddress.getNumberAddress());
        oldAddress.setState(newAddress.getState());
        oldAddress.setStreet(newAddress.getStreet());
    }
}

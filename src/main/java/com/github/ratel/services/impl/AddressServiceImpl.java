package com.github.ratel.services.impl;

import com.github.ratel.entity.Address;
import com.github.ratel.entity.User;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.AddressRepository;
import com.github.ratel.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public Address findById(Long id) {
        return this.addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
    }

    @Override
    public Address findByPhone(String phone) {
        return this.addressRepository.findByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
    }

    @Override
    public Address save(Address address) {
        return this.addressRepository.save(address);
    }

    @Override
    public Address update(Address address) {
        return this.addressRepository.save(address);
    }

    @Override
    public Address updateWithUser(Address address, User user) {
        address.addUser(user);
        return this.addressRepository.save(address);
    }

    @Override
    public void delete(Long id) {

    }
}

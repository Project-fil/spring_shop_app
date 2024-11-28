package com.github.ratel.services.impl;

import com.github.ratel.entity.Address;
import com.github.ratel.entity.User;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.AddressRepository;
import com.github.ratel.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    @Transactional(readOnly = true)
    public Address findById(Long id) {
        return this.addressRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Address findByPhone(String phone) {
        return this.addressRepository.findByPhoneAndRemovedFalse(phone)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
    }

    @Override
    @Transactional
    public Address save(Address address) {
        return this.addressRepository.save(address);
    }

    @Override
    @Transactional
    public Address updateWithUser(Address address, User user) {
        address.addUser(user);
        return this.addressRepository.save(address);
    }

    @Override
    @Transactional
    public void delete(Long id) {

    }
}

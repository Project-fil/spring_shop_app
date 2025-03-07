package com.github.ratel.services.impl;

import com.github.ratel.entity.Address;
import com.github.ratel.entity.User;
import com.github.ratel.exceptions.AppException;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.repositories.AddressRepository;
import com.github.ratel.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    @Transactional(readOnly = true)
    public Address findById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid params value: id(%s)", id);
        }
        return this.addressRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Address findByPhone(String phone) {
        if (Objects.isNull(phone)) {
            throw new AppException("Invalid params value: phone(%s)", phone);
        }
        return this.addressRepository.findByPhoneAndRemovedFalse(phone)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
    }

    @Override
    @Transactional
    public Address save(Address address) {
        if (Objects.isNull(address)) {
            throw new AppException("Invalid params value: address(%s)", address);
        }
        return this.addressRepository.save(address);
    }

    @Override
    @Transactional
    public Address updateWithUser(Address address, User user) {
        if (ObjectUtils.anyNull(address, user)) {
            throw new AppException("Invalid params value: address(%s), user(%s)", address, user);
        }
        address.addUser(user);
        return this.addressRepository.save(address);
    }

}

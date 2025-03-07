package com.github.ratel.services;

import com.github.ratel.entity.Address;
import com.github.ratel.entity.User;

public interface AddressService {

    Address findById(Long id);

    Address findByPhone(String phone);

    Address save(Address address);

    Address updateWithUser(Address address, User user);

}

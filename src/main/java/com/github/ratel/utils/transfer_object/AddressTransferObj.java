package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.Address;
import com.github.ratel.entity.User;
import com.github.ratel.payload.response.AddressResponse;
import com.github.ratel.payload.response.UserResponse;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class AddressTransferObj {

    public static AddressResponse fromAddress(Address payload) {
        if (Objects.isNull(payload)) {
            return null;
        }
        AddressResponse response = new AddressResponse();
        response.setId(payload.getId());
        response.setComment(payload.getComment());
        response.setPhone(payload.getPhone());
        response.setCountry(payload.getCountry());
        response.setCity(payload.getCity());
        response.setStreet(payload.getStreet());
        response.setHouseNumber(payload.getHouseNumber());
        response.setApartmentNumber(payload.getApartmentNumber());
        response.setUsers(ifExist(payload.getUsers()));
        response.setCreatedDate(payload.getCreatedDate().toString());
        response.setLastModifiedDate(payload.getLastModifiedDate().toString());
        return response;
    }

    public static AddressResponse fromLazyAddress(Address payload) {
        if (payload == null) {
            return null;
        }
        AddressResponse response = new AddressResponse();
        response.setId(payload.getId());
        response.setComment(payload.getComment());
        response.setPhone(payload.getPhone());
        response.setCountry(payload.getCountry());
        response.setCity(payload.getCity());
        response.setStreet(payload.getStreet());
        response.setHouseNumber(payload.getHouseNumber());
        response.setApartmentNumber(payload.getApartmentNumber());
        return response;
    }

    private static List<UserResponse> ifExist(List<User> users) {
        if (users.isEmpty()) {
            return List.of();
        } else {
            return users.stream().map(UserTransferObj::fromUserWithoutAddress).collect(Collectors.toList());
        }
    }

}

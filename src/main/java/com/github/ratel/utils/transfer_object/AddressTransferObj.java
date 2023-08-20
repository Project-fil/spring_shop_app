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
        return new AddressResponse(
                payload.getId(),
                payload.getComment(),
                payload.getPhone(),
                payload.getCountry(),
                payload.getCity(),
                payload.getStreet(),
                payload.getHouseNumber(),
                payload.getApartmentNumber(),
                ifExist(payload.getUserAddress()),
                payload.getCreatedDate(),
                payload.getLastModifiedDate()
        );
    }

    public static AddressResponse fromAddressWithoutUser(Address payload) {
        if (payload == null) {
            return null;
        }
        return new AddressResponse(
                payload.getId(),
                payload.getComment(),
                payload.getPhone(),
                payload.getCountry(),
                payload.getCity(),
                payload.getStreet(),
                payload.getHouseNumber(),
                payload.getApartmentNumber(),
                payload.getCreatedDate(),
                payload.getLastModifiedDate()
        );
    }

    private static List<UserResponse> ifExist(List<User> users) {
        if (users.isEmpty()) {
            return List.of();
        } else {
            return users.stream().map(UserTransferObj::fromUserWithoutAddress).collect(Collectors.toList());
        }
    }

}

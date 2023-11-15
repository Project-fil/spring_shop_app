package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.User;
import com.github.ratel.payload.request.UserUpdateRequest;
import com.github.ratel.payload.response.UserResponse;
import com.github.ratel.utils.EntityUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserTransferObj {

    public static UserResponse fromUser(User data) {
        return new UserResponse(
                data.getId(),
                data.getFirstname(),
                data.getLastname(),
                data.getEmail(),
                FileTransferObj.fromFile(data.getFileEntity()),
                AddressTransferObj.fromAddressWithoutUser(data.getAddress()),
                data.getRoles(),
                data.getVerification()
        );
    }

    public static UserResponse fromUserWithoutAddress(User data) {
        return new UserResponse(
                data.getId(),
                data.getFirstname(),
                data.getLastname(),
                data.getEmail(),
                data.getRoles(),
                data.getVerification()
        );
    }

    public static UserResponse fromUserForAdmin(User data) {
        return new UserResponse(
                data.getId(),
                data.getFirstname(),
                data.getLastname(),
                data.getEmail(),
                FileTransferObj.fromFile(data.getFileEntity()),
                AddressTransferObj.fromAddressWithoutUser(data.getAddress()),
                data.getRoles(),
                data.getVerification(),
                data.isRemoved(),
                data.getCratedAt(),
                data.getUpdatedAt()
        );
    }

    public static void updateUser(User user, UserUpdateRequest payload) {
        user.setFirstname(EntityUtil.updateField(user.getFirstname(), payload.getFirstname()));
        user.setLastname(EntityUtil.updateField(user.getLastname(), payload.getLastname()));
        user.getAddress().setPhone(EntityUtil.updateField(user.getAddress().getPhone(), payload.getPhone()));
        user.getAddress().setCountry(EntityUtil.updateField(user.getAddress().getCountry(), payload.getCountry()));
        user.getAddress().setCity(EntityUtil.updateField(user.getAddress().getCity(), payload.getCity()));
        user.getAddress().setStreet(EntityUtil.updateField(user.getAddress().getStreet(), payload.getStreet()));
        user.getAddress().setHouseNumber(EntityUtil.updateField(user.getAddress().getHouseNumber(), payload.getHouseNumber()));
        user.getAddress().setApartmentNumber(EntityUtil.updateField(user.getAddress().getApartmentNumber(), payload.getApartmentNumber()));
    }

}

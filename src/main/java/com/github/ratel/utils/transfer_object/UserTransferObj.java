package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.User;
import com.github.ratel.payload.request.UserUpdateRequest;
import com.github.ratel.payload.response.UserResponse;
import com.github.ratel.utils.UserUtil;
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
                data.getVerification(),
                data.isRemoved()
        );
    }

    public static UserResponse fromUserWithoutAddress(User data) {
        return new UserResponse(
                data.getId(),
                data.getFirstname(),
                data.getLastname(),
                data.getEmail(),
                data.getRoles(),
                data.getVerification(),
                data.isRemoved()
        );
    }

    public static void updateUser(User user, UserUpdateRequest payload) {
        user.setFirstname(UserUtil.updateField(user.getFirstname(), payload.getFirstname()));
        user.setLastname(UserUtil.updateField(user.getLastname(), payload.getLastname()));
        user.getAddress().setPhone(UserUtil.updateField(user.getAddress().getPhone(), payload.getPhone()));
        user.getAddress().setCountry(UserUtil.updateField(user.getAddress().getCountry(), payload.getCountry()));
        user.getAddress().setCity(UserUtil.updateField(user.getAddress().getCity(), payload.getCity()));
        user.getAddress().setStreet(UserUtil.updateField(user.getAddress().getStreet(), payload.getStreet()));
        user.getAddress().setHouseNumber(UserUtil.updateField(user.getAddress().getHouseNumber(), payload.getHouseNumber()));
        user.getAddress().setApartmentNumber(UserUtil.updateField(user.getAddress().getApartmentNumber(), payload.getApartmentNumber()));
    }

}

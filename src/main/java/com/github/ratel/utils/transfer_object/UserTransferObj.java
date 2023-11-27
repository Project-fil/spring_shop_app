package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.User;
import com.github.ratel.payload.request.UserUpdateRequest;
import com.github.ratel.payload.response.UserResponse;
import com.github.ratel.utils.EntityUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserTransferObj {

    public static UserResponse fromLazyUser(User data) {
        UserResponse response = new UserResponse();
        response.setId(data.getId());
        response.setFirstname(data.getFirstname());
        response.setLastname(data.getLastname());
        response.setEmail(data.getEmail());
        response.setImage(FileTransferObj.fromFile(data.getFileEntity()));
        response.setRole(data.getRoles());
        response.setVerification(data.getVerification());
        return response;
    }

    public static UserResponse fromUser(User data) {
        UserResponse response = new UserResponse();
        response.setId(data.getId());
        response.setFirstname(data.getFirstname());
        response.setLastname(data.getLastname());
        response.setEmail(data.getEmail());
        response.setImage(FileTransferObj.fromFile(data.getFileEntity()));
        response.setAddress(AddressTransferObj.fromLazyAddress(data.getAddress()));
        response.setCart(CartTransferObject.fromCart(data.getCart()));
        response.setRole(data.getRoles());
        response.setVerification(data.getVerification());
        return response;
    }

    public static UserResponse fromUserForAdmin(User data) {
        UserResponse response = new UserResponse();
        response.setId(data.getId());
        response.setFirstname(data.getFirstname());
        response.setLastname(data.getLastname());
        response.setEmail(data.getEmail());
        response.setImage(FileTransferObj.fromFileForAdmin(data.getFileEntity()));
        response.setAddress(AddressTransferObj.fromAddress(data.getAddress()));
        response.setCart(CartTransferObject.fromCart(data.getCart()));
        response.setRole(data.getRoles());
        response.setVerification(data.getVerification());
        response.setRemoved(data.isRemoved());
        response.setCreateAt(data.getCratedAt());
        response.setUpdateAt(data.getUpdatedAt());
        return response;
    }

    public static UserResponse fromUserWithoutAddress(User data) {
        UserResponse response = new UserResponse();
        response.setId(data.getId());
        response.setFirstname(data.getFirstname());
        response.setLastname(data.getLastname());
        response.setEmail(data.getEmail());
        response.setCart(CartTransferObject.fromCart(data.getCart()));
        response.setRole(data.getRoles());
        response.setVerification(data.getVerification());
        return response;
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

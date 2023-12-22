package com.github.ratel.utils.transfer_object;

import com.github.ratel.entity.Order;
import com.github.ratel.entity.User;
import com.github.ratel.payload.request.UserUpdateRequest;
import com.github.ratel.payload.response.OrderResponse;
import com.github.ratel.payload.response.UserResponse;
import com.github.ratel.utils.EntityUtil;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserTransferObj {

    public static UserResponse fromLazyUser(User payload) {
        UserResponse response = new UserResponse();
        response.setId(payload.getId());
        response.setFirstname(payload.getFirstname());
        response.setLastname(payload.getLastname());
        response.setEmail(payload.getEmail());
        response.setImage(FileTransferObj.fromFile(payload.getFileEntity()));
        response.setRole(payload.getRoles());
        response.setVerification(payload.getVerification());
        return response;
    }

    public static UserResponse fromUser(User payload) {
        UserResponse response = new UserResponse();
        response.setId(payload.getId());
        response.setFirstname(payload.getFirstname());
        response.setLastname(payload.getLastname());
        response.setEmail(payload.getEmail());
        response.setImage(FileTransferObj.fromFile(payload.getFileEntity()));
        response.setAddress(AddressTransferObj.fromLazyAddress(payload.getAddress()));
        response.setCart(CartTransferObject.fromCart(payload.getCart()));
        response.setOrders(ifExist(payload.getOrders()));
        response.setRole(payload.getRoles());
        response.setVerification(payload.getVerification());
        return response;
    }

    public static UserResponse fromUserForAdmin(User payload) {
        UserResponse response = new UserResponse();
        response.setId(payload.getId());
        response.setFirstname(payload.getFirstname());
        response.setLastname(payload.getLastname());
        response.setEmail(payload.getEmail());
        response.setImage(FileTransferObj.fromFileForAdmin(payload.getFileEntity()));
        response.setAddress(AddressTransferObj.fromAddress(payload.getAddress()));
        response.setCart(CartTransferObject.fromCart(payload.getCart()));
        response.setOrders(ifExist(payload.getOrders()));
        response.setRole(payload.getRoles());
        response.setVerification(payload.getVerification());
        response.setRemoved(payload.isRemoved());
        response.setCreateAt(payload.getCratedAt().toString());
        response.setUpdateAt(payload.getUpdatedAt().toString());
        return response;
    }

    public static UserResponse fromUserWithoutAddress(User payload) {
        UserResponse response = new UserResponse();
        response.setId(payload.getId());
        response.setFirstname(payload.getFirstname());
        response.setLastname(payload.getLastname());
        response.setEmail(payload.getEmail());
        response.setCart(CartTransferObject.fromCart(payload.getCart()));
        response.setRole(payload.getRoles());
        response.setVerification(payload.getVerification());
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

    private static List<OrderResponse> ifExist(List<Order> orders) {
        if (orders.isEmpty()) {
            return List.of();
        } else {
            return orders.stream().map(OrderTransferObj::fromLazyOrder).collect(Collectors.toList());
        }
    }

}

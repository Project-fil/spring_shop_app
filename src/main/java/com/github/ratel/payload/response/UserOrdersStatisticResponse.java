package com.github.ratel.payload.response;

import com.github.ratel.entity.enums.Roles;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserOrdersStatisticResponse {

    private Long userId;

    private String fullName;

    private String email;

    private AddressResponse address;

    private Roles role;

    private Integer totalNumberItems;

    private String totalAmountSpent;

    private Set<OrderStatisticResponse> orderStatisticResponseSet;

}

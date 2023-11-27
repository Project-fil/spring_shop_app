package com.github.ratel.payload.request;

import com.github.ratel.entity.enums.CartUpdateStatus;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {

    @NotNull
    private Long id;

    @NotNull
    private Map<Long, Integer> products;

    @NotNull
    private CartUpdateStatus updateStatus;

}

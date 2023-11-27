package com.github.ratel.payload.response;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    @NotNull
    private Long id;

    @NotNull
    private Map<ProductResponse, Integer> products;

}

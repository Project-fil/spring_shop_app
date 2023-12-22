package com.github.ratel.payload.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Map<Long, Integer> products;

    private String note;

}

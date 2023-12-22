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
    private Long Id;   // Possibly both a userId for creation and an orderId for updating.

    private Map<Long, Integer> products;

    private String note;

    private String orderStatus;

}

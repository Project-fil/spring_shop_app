package com.github.ratel.repositories;

import com.github.ratel.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    List<OrderDetails> findAllByOrderId(long orderId);

}

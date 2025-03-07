package com.github.ratel.repositories;

import com.github.ratel.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    List<OrderDetails> findAllByOrderIdAndRemovedFalse(long orderId);

    Optional<OrderDetails> findByIdAndRemovedFalse(long id);

}

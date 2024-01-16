package com.github.ratel.repositories;

import com.github.ratel.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // TODO: 16.01.2024 use Pageable fo all methods findAll

    @EntityGraph(attributePaths = "user")
    Optional<Order> findById(long id);

    List<Order> findAllByUserId(long id);

}

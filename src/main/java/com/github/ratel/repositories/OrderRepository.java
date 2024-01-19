package com.github.ratel.repositories;

import com.github.ratel.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = "user")
    Optional<Order> findByIdAndRemovedFalse(long id);

    Page<Order> findAllByUserIdAndRemovedFalse(long id, Pageable pageable);

    Page<Order> findAllByUserIdAndRemovedTrue(long id, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.id=:id AND (o.removed=false OR o.removed=true)")
    Optional<Order> findByIdForAdmin(@Param("id") long id);

}

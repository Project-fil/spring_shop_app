package com.github.ratel.repositories;

import com.github.ratel.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Modifying
    @Query(value = "UPDATE user_cart SET is_removed=true WHERE user_id=:userId", nativeQuery = true)
    void deleteCartByUserId(@Param("userId") long userId);

}

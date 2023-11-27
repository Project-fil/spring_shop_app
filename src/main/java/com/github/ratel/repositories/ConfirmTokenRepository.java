package com.github.ratel.repositories;

import com.github.ratel.entity.ConfirmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmTokenRepository extends JpaRepository<ConfirmToken, Long> {

    Optional<ConfirmToken> findByToken(String token);

//    @Modifying
//    @Query(value = "update ConfirmToken c set c.status=:status where c.id=:id")
//    void update(@Param(value = "id") Long id, @Param(value = "status") EntityStatus status);

}

package com.github.ratel.repositories;

import com.github.ratel.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByTokenAndRemovedFalse(String token);

//    @Modifying
//    @Query(value = "update VerificationToken c set c.status=:status where c.id=:id")
//    void update(@Param(value = "id") Long id, @Param(value = "status") EntityStatus status);

}

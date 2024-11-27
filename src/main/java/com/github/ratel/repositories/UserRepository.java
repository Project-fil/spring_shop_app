package com.github.ratel.repositories;

import com.github.ratel.entity.User;
import com.github.ratel.entity.enums.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //   @EntityGraph(attributePaths = {"address", "orders", "comments"})
    Optional<User> findByIdAndRemovedFalse(long id);

    Page<User> findAllByRemovedFalse(Pageable pageable);

    Optional<User> findByEmailAndRemovedFalse(String email);

    Optional<User> findUserByRolesAndRemovedFalse(Roles roles);

    Page<User> findAllByRemovedTrue(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.id=?1 AND (u.removed=false OR u.removed=true)")
    Optional<User> findUserByIdForAdmin(long id);

}

package com.github.ratel.repositories;

import com.github.ratel.entity.User;
import com.github.ratel.entity.enums.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndRemovedFalse(long id);

    Page<User> findAllByRemovedFalse(Pageable pageable);

    @Query(value = "SELECT u FROM User u JOIN Order o ON u.id = o.user.id WHERE u.id IN ?1 AND o.createdDate BETWEEN ?2 AND ?3")
    Page<User> findAllByUsersFilter(Set<Long> userIdSet, Date dateFrom, Date dateTo, Pageable pageable);

    @Query(value = "SELECT u FROM User u JOIN Order o ON u.id = o.user.id WHERE o.createdDate BETWEEN ?1 AND ?2")
    Page<User> findAllByDateFilter(Date dateFrom, Date dateTo, Pageable pageable);


    Optional<User> findByEmailAndRemovedFalse(String email);

    Optional<User> findUserByRolesAndRemovedFalse(Roles roles);

    Page<User> findAllByRemovedTrue(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.id=?1 AND (u.removed=false OR u.removed=true)")
    Optional<User> findUserByIdForAdmin(long id);

}

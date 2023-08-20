package com.github.ratel.repositories;

import com.github.ratel.entity.User;
import com.github.ratel.entity.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   List<User> findAllByRemovedFalse();

   Optional <User> findByEmail(String email);

   Optional <User> findUserByRoles(Roles roles);

   Optional <User> findUserByIdAndRemovedFalse(long id);

}

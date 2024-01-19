package com.github.ratel.repositories;

import com.github.ratel.entity.Address;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    // TODO: 16.01.2024 create methods for admin (maybe)

    @EntityGraph(attributePaths = "users")
    Optional<Address> findByIdAndRemovedFalse(long id);

    Optional<Address> findByPhoneAndRemovedFalse(String phone);



}

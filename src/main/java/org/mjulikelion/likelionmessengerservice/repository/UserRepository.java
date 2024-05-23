package org.mjulikelion.likelionmessengerservice.repository;

import org.mjulikelion.likelionmessengerservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmployeeNumber(String employeeNumber);
}

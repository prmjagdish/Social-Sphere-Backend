package com.jagdish.SocailSphere.repository;

import com.jagdish.SocailSphere.model.entity.User;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Id> {
    Optional<User> findByEmail(String email);
}

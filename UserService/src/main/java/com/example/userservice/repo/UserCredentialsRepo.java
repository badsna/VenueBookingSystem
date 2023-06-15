package com.example.userservice.repo;

import com.example.userservice.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepo extends JpaRepository<UserCredentials, Long> {
      Optional<UserCredentials> findByEmail(String username);
}

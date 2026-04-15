package com.prueba.userservice.repository;

import com.prueba.userservice.model.User;
import com.prueba.userservice.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);

    Optional<VerificationToken> findFirstByUserAndUsedFalse(User user);
}
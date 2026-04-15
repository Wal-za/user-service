package com.prueba.userservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expirationDate;

    private boolean used;

    // 🔥 CAMBIO CLAVE: ManyToOne en lugar de OneToOne
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
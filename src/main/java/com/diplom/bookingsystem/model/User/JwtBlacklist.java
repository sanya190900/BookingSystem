package com.diplom.bookingsystem.model.User;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "jwt_blacklist")
public class JwtBlacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long jwtBlacklist_id;

    private String token;

    private LocalDateTime expiryDate;
}

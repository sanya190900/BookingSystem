package com.diplom.bookingsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    @Enumerated(EnumType.STRING)
    private ERole role;

    public Role(ERole role) {
        this.role = role;
    }
}

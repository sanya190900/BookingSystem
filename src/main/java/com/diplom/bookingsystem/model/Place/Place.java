package com.diplom.bookingsystem.model.Place;

import com.diplom.bookingsystem.model.Address;
import com.diplom.bookingsystem.model.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@Table(name = "places",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long place_id;

    @NotBlank
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    private Address address;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonIgnore
    private User user;

    @NotBlank
    private String name;

    @NotBlank
    @Lob
    private String description;

    public Place(Address address, String name, String description) {
        this.address = address;
        this.name = name;
        this.description = description;
    }
}

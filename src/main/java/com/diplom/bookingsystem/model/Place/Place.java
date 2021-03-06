package com.diplom.bookingsystem.model.Place;

import com.diplom.bookingsystem.model.Address;
import com.diplom.bookingsystem.model.Comment.Comment;
import com.diplom.bookingsystem.model.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
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
    private User user;

    @NotBlank
    private String name;

    @NotBlank
    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private Set<Gallery> gallery;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private Set<Schedule> schedule;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "places_services",
            joinColumns = @JoinColumn(name = "place_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<Service> services = new HashSet<>();

    public Place(Address address, String name, String description) {
        this.address = address;
        this.name = name;
        this.description = description;
    }
}

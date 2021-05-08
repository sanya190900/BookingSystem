package com.diplom.bookingsystem.model.Comment;

import com.diplom.bookingsystem.model.Place.Place;
import com.diplom.bookingsystem.model.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long comment_id;

    @NotBlank
    @Column(columnDefinition = "text")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false, updatable = false)
    @JsonIgnore
    private Place place;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonIgnore
    private User user;

    public Comment(String comment, Place place, User user) {
        this.comment = comment;
        this.place = place;
        this.user = user;
    }

    // TODO: front end
}

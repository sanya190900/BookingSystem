package com.diplom.bookingsystem.dto.Comment;

import com.diplom.bookingsystem.model.Place.Place;
import com.diplom.bookingsystem.model.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommentDto {
    private Long comment_id;

    private String comment;

    private Place place;

    private User user;
}

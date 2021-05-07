package com.diplom.bookingsystem.service.Comment;

import com.diplom.bookingsystem.dto.Comment.CommentDto;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<?> createComment(CommentDto commentDto);

    ResponseEntity<?> deleteComment(Long id);
}

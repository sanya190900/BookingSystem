package com.diplom.bookingsystem.controller;

import com.diplom.bookingsystem.dto.Comment.CommentDto;
import com.diplom.bookingsystem.repository.CommentRepository;
import com.diplom.bookingsystem.service.Comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/place/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @PostMapping
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentDto commentDto) {
        System.out.println(commentDto);
        return commentService.createComment(commentDto);
    }

    @PreAuthorize("#commentDto.user.user_id == authentication.principal.id or hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> deleteComment(@Valid @RequestBody CommentDto commentDto) {
        return commentService.deleteComment(commentDto.getComment_id());
    }
}

package com.diplom.bookingsystem.service.Comment.Impl;

import com.diplom.bookingsystem.dto.Comment.CommentDto;
import com.diplom.bookingsystem.model.Comment.Comment;
import com.diplom.bookingsystem.model.User.User;
import com.diplom.bookingsystem.repository.CommentRepository;
import com.diplom.bookingsystem.repository.UserRepository;
import com.diplom.bookingsystem.service.Comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<?> createComment(CommentDto commentDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + auth.getName()));

        Comment comment = new Comment(
                commentDto.getComment(),
                commentDto.getPlace(),
                user);

        return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> deleteComment(Long id) {
        commentRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

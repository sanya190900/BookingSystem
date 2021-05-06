package com.diplom.bookingsystem.repository;

import com.diplom.bookingsystem.model.Comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(exported = false)
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}

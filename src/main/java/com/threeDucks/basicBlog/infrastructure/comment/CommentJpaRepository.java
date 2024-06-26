package com.threeDucks.basicBlog.infrastructure.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentEntity,Long> {
}

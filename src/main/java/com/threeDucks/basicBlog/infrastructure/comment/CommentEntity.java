package com.threeDucks.basicBlog.infrastructure.comment;

import com.threeDucks.basicBlog.infrastructure.article.ArticleEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    private ArticleEntity articleEntity;

    @Setter @Column(nullable = false, length = 500)
    private String content;

    @CreatedDate @Column(nullable = false)
    private LocalDateTime createdAt;

    @CreatedBy @Column(nullable = false, length = 100)
    private String createdBy;

    @LastModifiedDate @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @LastModifiedBy
    @Column(nullable = false, length = 100) private String modifiedBy;


    private CommentEntity(ArticleEntity articleEntity, String content) {
        this.articleEntity = articleEntity;
        this.content = content;
    }

    public static CommentEntity of(ArticleEntity articleEntity, String content) {
        return new CommentEntity(articleEntity, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
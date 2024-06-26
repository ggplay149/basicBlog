package com.threeDucks.basicBlog.infrastructure.article;

import com.threeDucks.basicBlog.infrastructure.comment.CommentEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false)
    private String title;

    @Setter @Column(nullable = false)
    private String content;

    @Setter
    private String hashtag;

    @ToString.Exclude //toString 제외
    @OrderBy("id")
    @OneToMany(mappedBy = "articleEntity", cascade = CascadeType.ALL)
    private final Set<CommentEntity> comments = new LinkedHashSet<>();

    @CreatedDate @Column(nullable = false)
    private LocalDateTime createdAt;

    @CreatedBy @Column(nullable = false, length = 100)
    private String createdBy;

    @LastModifiedDate @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @LastModifiedBy @Column(nullable = false)
    private String modifiedBy;

    private ArticleEntity(String title, String content, String hashtag){
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static ArticleEntity of (String title, String content, String hashtag){
        return new ArticleEntity(title,content,hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleEntity that = (ArticleEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package com.threeDucks.basicBlog.domain.infrastructure;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title")
})
@Entity
public class ArticlesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false)
    private String title; // 제목

    @Setter @Column(nullable = false)
    private String content; // 본문

    @Setter
    private String hashtag; // 해시태그

    @ToString.Exclude //toString 제외
    @OrderBy("id")
    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    private final Set<CommentEntity> comments = new LinkedHashSet<>();

    @CreatedDate @Column(nullable = false)
    private LocalDateTime createdAt; // 생성일시

    @CreatedBy @Column(nullable = false)
    private String createdBy; // 생성자

    @LastModifiedDate @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일시

    @LastModifiedBy @Column(nullable = false)
    private String modifiedBy; // 수정자

}

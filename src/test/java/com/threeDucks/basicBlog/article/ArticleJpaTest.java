package com.threeDucks.basicBlog.article;

import com.threeDucks.basicBlog.config.JpaConfig;
import com.threeDucks.basicBlog.infrastructure.article.ArticleEntity;
import com.threeDucks.basicBlog.infrastructure.article.ArticleJpaRepository;
import com.threeDucks.basicBlog.infrastructure.comment.CommentJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Article JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
public class ArticleJpaTest {

    private final ArticleJpaRepository articleJpaRepository;
    private final CommentJpaRepository commentJpaRepository;

    @Autowired
    public ArticleJpaTest(ArticleJpaRepository articleJpaRepository, CommentJpaRepository commentJpaRepository) {
        this.articleJpaRepository = articleJpaRepository;
        this.commentJpaRepository = commentJpaRepository;
    }

    @DisplayName("article select test")
    @Test
    void givenTestData_whenSelecting_thenWorksFine(){
        //Given
        //When
        List<ArticleEntity> articleEntities = articleJpaRepository.findAll();
        //Then
        assertThat(articleEntities).isNotNull().hasSize(10);
    }

    @DisplayName("article insert test")
    @Test
    void givenTestData_whenInserting_thenWorksFine(){
        //Given
        long testCount = articleJpaRepository.count();
        //When
        articleJpaRepository.save(ArticleEntity.of("title2","content2","hashtag2"));
        //Then
        assertThat(articleJpaRepository.count()).isEqualTo(testCount+1);
    }

    @DisplayName("article update test")
    @Test
    void givenTestData_whenUpdating_thenWorksFine(){
        //Given
        ArticleEntity articleEntity = articleJpaRepository.findById(1L).orElseThrow();
        articleEntity.setTitle("title2");
        //When
        String testTitle = articleJpaRepository.saveAndFlush(articleEntity).getTitle();
        //Then
        assertThat(testTitle).isEqualTo("title2");
    }

    @DisplayName("article delete test")
    @Test
    void givenTestData_whenDeleteing_thenWorksFine(){
        //Given
        long previousArticleCount = articleJpaRepository.count();
        long previousCommentCount = commentJpaRepository.count();

        //When
        articleJpaRepository.deleteById(1L);

        //Then
        assertThat(articleJpaRepository.count()).isEqualTo(previousArticleCount-1);
        assertThat(commentJpaRepository.count()).isEqualTo(previousCommentCount-3);
    }
}

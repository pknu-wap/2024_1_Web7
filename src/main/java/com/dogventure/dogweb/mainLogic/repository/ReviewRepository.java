package com.dogventure.dogweb.mainLogic.repository;

import com.dogventure.dogweb.mainLogic.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// 엔티티에 의해 생성된 데이터베이스 테이블에 접근하는 메서드들을 사용하기 위한 인터페이스
// CRUD 어떻게 처리할지 정의하는 계층
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findUserByTitle(String title);

}

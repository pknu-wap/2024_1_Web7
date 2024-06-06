package com.dogventure.dogweb.mainLogic.repository;

import com.dogventure.dogweb.mainLogic.entity.Place;
import com.dogventure.dogweb.mainLogic.entity.Review;
import com.dogventure.dogweb.mainLogic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByUserAndPlace(User user, Place place);

    Integer countAllByUser(User user);

    Optional<Review> findByUserAndPlace(User user, Place place);
}

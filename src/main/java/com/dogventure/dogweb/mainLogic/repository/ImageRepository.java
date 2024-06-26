package com.dogventure.dogweb.mainLogic.repository;

import com.dogventure.dogweb.mainLogic.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}

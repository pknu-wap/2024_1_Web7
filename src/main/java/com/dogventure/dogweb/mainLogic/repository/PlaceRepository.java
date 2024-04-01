package com.dogventure.dogweb.mainLogic.repository;

import com.dogventure.dogweb.mainLogic.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findByNameContaining(String name);
}

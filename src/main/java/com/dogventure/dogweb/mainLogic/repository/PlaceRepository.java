package com.dogventure.dogweb.mainLogic.repository;

import com.dogventure.dogweb.constant.DogSize;
import com.dogventure.dogweb.constant.PlaceType;
import com.dogventure.dogweb.mainLogic.entity.Place;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findByNameContaining(String name);

    List<Place> findByPlaceType(PlaceType type);

    List<Place> findByDogSize(DogSize size);

    List<Place> findByPlaceTypeAndDogSize(PlaceType placeType, DogSize dogSize);
}

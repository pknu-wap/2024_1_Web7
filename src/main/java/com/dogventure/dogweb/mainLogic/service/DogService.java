package com.dogventure.dogweb.mainLogic.service;

import com.dogventure.dogweb.dto.mainLogic.mypage.request.DogRequestDto;
import com.dogventure.dogweb.mainLogic.entity.Dog;
import com.dogventure.dogweb.mainLogic.entity.Image;
import com.dogventure.dogweb.mainLogic.entity.User;
import com.dogventure.dogweb.mainLogic.repository.DogRepository;
import com.dogventure.dogweb.mainLogic.repository.ImageRepository;
import com.dogventure.dogweb.mainLogic.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DogService {

    private final DogRepository dogRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public void setImage(MultipartFile file) throws IOException {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("토큰 인증을 받은 사용자가 존재하지 않습니다"));

        Dog dog = user.getDog();

        if (dog == null) {
            Image image = new Image(file.getOriginalFilename(), file.getBytes());
            imageRepository.save(image);
            dog = new Dog(null, null, null, null, image);
            dogRepository.save(dog);
            user.setDog(dog);
            userRepository.save(user);
        } else {
            Image image = new Image(file.getOriginalFilename(), file.getBytes());
            imageRepository.save(image);
            dog.setImage(image);
            dogRepository.save(dog);
            user.setDog(dog);
            userRepository.save(user);
        }
    }

    public void setDetail(DogRequestDto request) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("토큰 인증을 받은 사용자가 존재하지 않습니다"));

        Dog dog = user.getDog();

        if (dog == null) {
            dog = new Dog(request.getDogName(), request.getSpecies(), request.getGender(), request.getRegistrationNumber(), null);
            dogRepository.save(dog);
            user.setDog(dog);
            userRepository.save(user);
        } else {
            dog.setDogName(request.getDogName());
            dog.setSpecies(request.getSpecies());
            dog.setGender(request.getGender());
            dog.setRegistrationNumber(request.getRegistrationNumber());
            dogRepository.save(dog);
            user.setDog(dog);
            userRepository.save(user);
        }
    }
}

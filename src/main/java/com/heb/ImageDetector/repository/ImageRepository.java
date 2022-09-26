package com.heb.ImageDetector.repository;

import com.heb.ImageDetector.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends MongoRepository<Image, String> {
    @Query("{ 'id' : ?0 }")
    Image findImagesById(String imageId);

    @Query("{'objects' : { $all: ?0 } }")
    List<Image> findImagesByObject(String[] objects);
}


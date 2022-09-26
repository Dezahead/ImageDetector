package com.heb.ImageDetector.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@Document("pictures")
public class Image {
    @Id
    private String id;

    private MultipartFile imageFile;
    private String imageURL;
    private String label;
    private Binary imageBytes;
    private List<String> objects;
    private boolean enableObjectDetection;
}

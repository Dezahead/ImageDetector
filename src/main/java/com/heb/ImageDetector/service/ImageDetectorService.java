package com.heb.ImageDetector.service;

import com.heb.ImageDetector.Exception.InvalidQueryParameterException;
import com.heb.ImageDetector.client.ImaggaClient;
import com.heb.ImageDetector.representation.Root;
import com.heb.ImageDetector.representation.Tag;
import com.heb.ImageDetector.model.Image;
import com.heb.ImageDetector.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ImageDetectorService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImaggaClient imaggaClient;


    public List<Image> getAllImages(){
        log.info("Retrieving all images");
        return imageRepository.findAll();
    }

    public Image getImageById(String imageId){
        return imageRepository.findImagesById(imageId);
    }

    public List<Image> getImagesByObject(Optional<List<String>> tags) throws InvalidQueryParameterException {
        if(tags.isPresent()) {
            String[] itemsArray = new String[tags.get().size()];
            itemsArray = tags.get().toArray(itemsArray);
            return imageRepository.findImagesByObject(itemsArray);
        }
        else{
            throw new InvalidQueryParameterException("Query parameters are invalid: " + tags);
        }
    }

    public Image storeImage(Image image) throws IOException, URISyntaxException {
        if(image.getImageURL().isEmpty()) {
            image.setImageBytes(new Binary(BsonBinarySubType.BINARY, image.getImageFile().getBytes()));
        }

        if(image.isEnableObjectDetection())
            image.setObjects(getDetectedObjects(image));

        image = imageRepository.save(image);
        return image;
    }

    private List<String> getDetectedObjects(Image image) throws IOException{
        List<String> tagList = new ArrayList<>();
        Root result = imaggaClient.getTags(image);
        log.info(result.toString());
        for(Tag tag: result.getResult().getTags()){
            tagList.add(tag.getTag().getEn());
        }

        return tagList;
    }
}

package com.heb.ImageDetector.controller;

import com.heb.ImageDetector.Exception.InvalidQueryParameterException;
import com.heb.ImageDetector.model.Image;
import com.heb.ImageDetector.service.ImageDetectorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/imagedetector/images")
@Slf4j
public class ImageController {
    ImageDetectorService imageDetectionService;

    @Autowired
    public ImageController(ImageDetectorService imageDetectionService) {
        this.imageDetectionService = imageDetectionService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Image>> getImages(@RequestParam(name="objects", required=false) Optional<List<String>> objects){
        if (objects.isPresent()) {
            try {
                return new ResponseEntity<>(imageDetectionService.getImagesByObject(objects), HttpStatus.OK);
            } catch (InvalidQueryParameterException e) {
                log.error(e.message);
            }
        }
        return new ResponseEntity<>(imageDetectionService.getAllImages(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{imageId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Image> getImages(@PathVariable(value="imageId") String imageId){
        try{
            return new ResponseEntity<>(imageDetectionService.getImageById(imageId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Image getImages(@RequestBody Image image){
        try {
            return imageDetectionService.storeImage(image);
        } catch (IOException | URISyntaxException e) {
            log.error("Failed while attempting to get Image tags");
            e.printStackTrace();
            return image;
        }
    }
}

package com.heb.ImageDetector.service;

import com.heb.ImageDetector.client.ImaggaClient;
import com.heb.ImageDetector.model.Image;
import com.heb.ImageDetector.repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceTestConfiguration.class})
public class ImageDetectorServiceTest {
    @MockBean
    ImageRepository imageRepository;

    @MockBean
    ImaggaClient imaggaClient;

    @Autowired
    private ImageDetectorService imageDetectorService;

    private Image image;

    @BeforeEach
    public void setUp(){
        image = Image.builder()
                .id("1234")
                .imageURL("C:\\test\\pic")
                .enableObjectDetection(true)
                .label("test picture")
                .objects(new ArrayList<>(Arrays.asList("wood", "old")))
                .build();
    }

    @Test
    public void getAllImages_EmptyCollection(){
        doReturn(new ArrayList<>()).when(imageRepository).findAll();

        assertTrue(imageDetectorService.getAllImages().isEmpty());
    }

    @Test
    public void getAllImages_NotEmptyCollection(){
        doReturn(new ArrayList<>(Arrays.asList(image))).when(imageRepository).findAll();
        List<Image> result = imageDetectorService.getAllImages();
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getLabel(), equalTo("test picture"));
    }

}

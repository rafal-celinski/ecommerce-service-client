package pis24l.projekt.api_client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import pis24l.projekt.api_client.services.ImageAddService;
import pis24l.projekt.api_client.repositories.mongo.ImageRepository;

import pis24l.projekt.api_client.models.Image;

import java.io.IOException;


@RestController
@RequestMapping("/images")
public class ImageAddController {

    private final ImageAddService imageAddService;

    private final ImageRepository imageRepository;

    @Autowired
    public ImageAddController(ImageRepository imageRepository, ImageAddService imageAddService) {
        this.imageRepository = imageRepository;
        this.imageAddService = imageAddService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFile(@RequestParam("image") MultipartFile file, @RequestParam("productId") String productId) {
        if (!imageAddService.isImageFile(file)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file type.");
        }
        try {
            Image image = new Image(productId);
            Image savedImage = imageRepository.save(image);

            imageAddService.uploadImage(file, image.getId());
            return ResponseEntity.ok(savedImage);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload the file: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other exceptions that might not have been anticipated
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}


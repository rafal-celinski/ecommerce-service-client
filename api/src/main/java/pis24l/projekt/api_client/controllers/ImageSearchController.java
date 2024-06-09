package pis24l.projekt.api_client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pis24l.projekt.api_client.services.ImageSearchService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5000")
@RestController
@RequestMapping("/images")
public class ImageSearchController {

    private final ImageSearchService imageSearchService;

    @Autowired
    public ImageSearchController(ImageSearchService imageSearchService) {
        this.imageSearchService = imageSearchService;
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<?> getImageById(@PathVariable Long imageId) {
        try {
            Optional<Path> filePathOptional = imageSearchService.findImageFile(imageId);
            if (filePathOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found.");
            }

            Path filePath = filePathOptional.get();
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found.");
            }

            String contentType = Files.probeContentType(filePath);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve the file: " + e.getMessage());
        }
    }
}

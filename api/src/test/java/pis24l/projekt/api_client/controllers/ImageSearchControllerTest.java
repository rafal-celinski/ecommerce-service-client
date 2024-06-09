package pis24l.projekt.api_client.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pis24l.projekt.api_client.services.ImageSearchService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ImageSearchControllerTest {

    @Mock
    private ImageSearchService imageSearchService;

    @InjectMocks
    private ImageSearchController imageSearchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetImageById_ImageFound() throws Exception {
        Long imageId = 1L;
        Path filePath = Paths.get("uploads", imageId + ".jpg");

        // Ensure the file exists for the test
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        }

        Resource resource = new UrlResource(filePath.toUri());

        when(imageSearchService.findImageFile(imageId)).thenReturn(Optional.of(filePath));

        ResponseEntity<?> responseEntity = imageSearchController.getImageById(imageId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(resource, responseEntity.getBody());
        assertEquals("inline; filename=\"" + resource.getFilename() + "\"", responseEntity.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));

        // Clean up the created file after the test
        Files.deleteIfExists(filePath);
    }

    @Test
    void testGetImageById_ImageNotFound() {
        Long imageId = 1L;

        when(imageSearchService.findImageFile(imageId)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = imageSearchController.getImageById(imageId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("File not found.", responseEntity.getBody());
    }

    @Test
    void testGetImageById_InternalServerError() throws Exception {
        Long imageId = 1L;

        when(imageSearchService.findImageFile(imageId)).thenThrow(new RuntimeException("Error"));

        ResponseEntity<?> responseEntity = imageSearchController.getImageById(imageId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Failed to retrieve the file: Error", responseEntity.getBody());
    }
}

package pis24l.projekt.api_client.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImageSearchServiceTest {

    private ImageSearchService imageSearchService;
    private Path tempDir;

    @BeforeEach
    void setUp() throws Exception {
        // Create a temporary directory for testing
        tempDir = Files.createTempDirectory("uploads");
        imageSearchService = new ImageSearchService(tempDir.toString());
    }

    @AfterEach
    void tearDown() throws Exception {
        // Clean up the temporary directory after each test
        Files.walk(tempDir)
                .map(Path::toFile)
                .forEach(file -> {
                    if (!file.delete()) {
                        file.deleteOnExit();
                    }
                });
    }

    @Test
    void testFindImageFile_FileFound() throws Exception {
        String imageId = "abba";
        Path jpgFilePath = tempDir.resolve(imageId + ".jpg");
        Files.createFile(jpgFilePath);

        Optional<Path> result = imageSearchService.findImageFile(imageId);

        assertTrue(result.isPresent());
        assertEquals(jpgFilePath, result.get());
    }

    @Test
    void testFindImageFile_FileNotFound() {
        String imageId = "abba";

        Optional<Path> result = imageSearchService.findImageFile(imageId);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindImageFile_MultipleExtensions() throws Exception {
        String imageId = "abba";
        Path pngFilePath = tempDir.resolve(imageId + ".png");
        Files.createFile(pngFilePath);

        Optional<Path> result = imageSearchService.findImageFile(imageId);

        assertTrue(result.isPresent());
        assertEquals(pngFilePath, result.get());
    }
}

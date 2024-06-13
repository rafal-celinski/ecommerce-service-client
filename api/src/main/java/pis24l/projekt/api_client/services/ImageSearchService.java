//package pis24l.projekt.api_client.services;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ImageSearchService {
//
//    private final String uploadDir;
//    private final List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp");
//
//    public ImageSearchService(@Value("${upload.dir}") String uploadDir) {
//        this.uploadDir = uploadDir;
//    }
//
//    public Optional<Path> findImageFile(String imageId) {
//        for (String extension : allowedExtensions) {
//            Path filePath = Paths.get(uploadDir, imageId + "." + extension);
//            if (Files.exists(filePath)) {
//                return Optional.of(filePath);
//            }
//        }
//        return Optional.empty();
//    }
//}

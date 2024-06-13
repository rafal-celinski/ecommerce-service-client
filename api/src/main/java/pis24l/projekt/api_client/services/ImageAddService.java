//package pis24l.projekt.api_client.services;
//
//import org.apache.commons.io.FilenameUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Service
//public class ImageAddService {
//    private static final String DIRECTORY = System.getProperty("user.dir") + "/uploads";
//    private final FileStorage fileStorage;
//
//    @Autowired
//    public ImageAddService(FileStorage fileStorage) {
//        this.fileStorage = fileStorage;
//    }
//
//    public void uploadImage(MultipartFile file, String id) throws IOException {
//        Path directoryPath = Paths.get(DIRECTORY);
//        if (!Files.exists(directoryPath)) {
//            Files.createDirectories(directoryPath);
//        }
//        String fileName = file.getOriginalFilename();
//        String fileExtension = FilenameUtils.getExtension(fileName);
//        String saveFileName = id + "." + fileExtension;
//        fileStorage.storeFile(directoryPath, file, saveFileName);
//    }
//
//
//    public boolean isImageFile(MultipartFile file) {
//        String fileName = file.getOriginalFilename();
//        if (fileName == null) return false;
//
//        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
//        return fileExtension.equals("jpg") ||
//                fileExtension.equals("jpeg") ||
//                fileExtension.equals("png") ||
//                fileExtension.equals("gif") ||
//                fileExtension.equals("bmp");
//    }
//}
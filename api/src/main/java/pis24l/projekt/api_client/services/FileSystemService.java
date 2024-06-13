//package pis24l.projekt.api_client.services;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Path;
//@Service
//public class FileSystemService implements FileStorage {
//    @Override
//    public void storeFile(Path directoryPath, MultipartFile file, String saveFileName) throws IOException {
//        Path filePath = directoryPath.resolve(saveFileName);
//        file.transferTo(filePath);
//    }
//}

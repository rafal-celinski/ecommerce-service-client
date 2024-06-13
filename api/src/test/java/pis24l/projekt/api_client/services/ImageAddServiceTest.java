//package pis24l.projekt.api_client.services;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import org.springframework.mock.web.MockMultipartFile;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.verify;
//
//import static org.mockito.Mockito.times;
//
//import java.io.IOException;
//import java.nio.file.Path;
//
//
//@ExtendWith(MockitoExtension.class)
//class ImageAddServiceTest {
//
//    @InjectMocks
//    private ImageAddService imageAddService;
//
//    @Mock
//    private FileStorage mockFileStorage;
//
//    @Test
//    void uploadImage_whenCalled_verifiesMethodCalls() throws IOException {
//        MockMultipartFile file = new MockMultipartFile("image", "filename.jpg", "image/jpeg", "some data".getBytes());
//        Long id = 1L;
//        String saveFileName = id + ".jpg";
//
//        imageAddService.uploadImage(file, id);
//
//        verify(mockFileStorage, times(1)).storeFile(any(Path.class), eq(file), eq(saveFileName));
//    }
//
//    @Test
//    void isImageFile_whenCalledWithImageFile_returnsTrue() {
//        MockMultipartFile file = new MockMultipartFile("data", "test.jpg", "image/jpeg", new byte[0]);
//        assertTrue(imageAddService.isImageFile(file));
//    }
//
//    @Test
//    void isImageFile_whenCalledWithNonImageFile_returnsFalse() {
//        MockMultipartFile file = new MockMultipartFile("data", "test.txt", "text/plain", new byte[0]);
//        assertFalse(imageAddService.isImageFile(file));
//    }
//}
//

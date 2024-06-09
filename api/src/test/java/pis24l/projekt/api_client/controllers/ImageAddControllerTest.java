package pis24l.projekt.api_client.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import pis24l.projekt.api_client.repositories.ImageRepository;
import pis24l.projekt.api_client.services.ImageAddService;

import pis24l.projekt.api_client.models.Image;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ImageAddController.class)
public class ImageAddControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageRepository imageRepository;

    @MockBean
    private ImageAddService imageAddService;

    @Test
    public void addFile_WhenImageIsValid_ThenReturnImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image content".getBytes());

        when(imageAddService.isImageFile(any())).thenReturn(true);
        when(imageRepository.save(any(Image.class))).thenAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(multipart("/images/add")
                        .file(file)
                        .param("productId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void addFile_WhenImageIsInvalid_ThenReturnBadRequest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "test.txt", "text/plain", "This is not an image".getBytes());

        when(imageAddService.isImageFile(any())).thenReturn(false);

        mockMvc.perform(multipart("/images/add")
                        .file(file)
                        .param("productId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid file type."));
    }
}


package com.tirestore.tireshop.service;

import com.tirestore.tireshop.controller.admin.AdminItemController;
import com.tirestore.tireshop.dto.request.WheelRequest;
import com.tirestore.tireshop.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminItemController.class)
class AdminItemControllerTest {

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WheelService wheelService;

    @MockitoBean
    private TireService tireService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void addWheel_returnsOk() throws Exception {
        WheelRequest request = new WheelRequest(
                "wheelRequest", "Just Wheel", BigDecimal.valueOf(150.30),
                10, 14, BigDecimal.valueOf(30.4), BigDecimal.valueOf(12.3), 4);

        MockMultipartFile wheelPart = new MockMultipartFile(
                "wheelRequest", "", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(request));

        MockMultipartFile img = new MockMultipartFile(
                "image", "test.png", MediaType.IMAGE_PNG_VALUE, "fake_image".getBytes());

        mockMvc.perform(multipart("/api/admin/item/add-wheel")
                        .file(wheelPart)
                        .file(img)
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}
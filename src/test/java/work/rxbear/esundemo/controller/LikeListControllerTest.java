package work.rxbear.esundemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import work.rxbear.esundemo.dto.LikeListRequest;
import work.rxbear.esundemo.exception.BadRequestException;
import work.rxbear.esundemo.exception.GlobalExceptionHandler;
import work.rxbear.esundemo.exception.NotFoundException;
import work.rxbear.esundemo.model.ApiResponse;
import work.rxbear.esundemo.service.LikeListService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LikeListControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private LikeListService likeListService;

    @InjectMocks
    private LikeListController likeListController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(likeListController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }


    @Test
    void testAddLikeItem() throws Exception {
        LikeListRequest request = new LikeListRequest("U001", "基金A", new BigDecimal("100.00"),
                new BigDecimal("0.015"), "ACC123", 2);

        doNothing().when(likeListService).addLikeItem(any());

        mockMvc.perform(post("/api/like-list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("新增成功"));
    }

    @Test
    void testAddLikeItem_BadRequest() throws Exception {
        LikeListRequest request = new LikeListRequest("U001", "基金錯誤", BigDecimal.ZERO,
                BigDecimal.ZERO, "ACC123", 0);

        doThrow(new BadRequestException("參數有誤")).when(likeListService).addLikeItem(any());

        mockMvc.perform(post("/api/like-list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("參數有誤"));
    }

    @Test
    void testGetLikeListByUserId() throws Exception {
        when(likeListService.getLikeListByUserId("U001")).thenReturn(
                List.of(Map.of(
                        "sn", 1,
                        "productName", "基金A",
                        "account", "ACC123",
                        "orderNum", 2,
                        "totalFee", new BigDecimal("3.00"),
                        "totalAmount", new BigDecimal("203.00"),
                        "email", "test@mail.com"
                )));

        mockMvc.perform(get("/api/like-list?userId=U001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("查詢成功"))
                .andExpect(jsonPath("$.data[0].productName").value("基金A"));
    }

    @Test
    void testGetLikeList_NotFound() throws Exception {
        doThrow(new NotFoundException("查無使用者")).when(likeListService).getLikeListByUserId("U999");

        mockMvc.perform(get("/api/like-list?userId=U999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("查無使用者"));
    }

    @Test
    void testUpdateLikeItem() throws Exception {
        LikeListRequest request = new LikeListRequest(null, "基金B", new BigDecimal("120.00"),
                new BigDecimal("0.02"), "ACC123", 1);

        doNothing().when(likeListService).updateLikeItem(eq(1), any());

        mockMvc.perform(put("/api/like-list/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("更新成功"));
    }

    @Test
    void testDeleteLikeItem() throws Exception {
        doNothing().when(likeListService).deleteLikeItem(1);

        mockMvc.perform(delete("/api/like-list/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("刪除成功"));
    }
}

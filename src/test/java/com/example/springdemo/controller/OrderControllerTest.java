package com.example.springdemo.controller;

import com.example.springdemo.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void getOrder() throws Exception {
        when(orderService.getAllOrders(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/MyOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"UserId\":\"1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(orderService).getAllOrders(1);
    }

    @Test
    void addOrder() throws Exception {
        doNothing().when(orderService).AddNewOrder(anyInt(), anyInt(), any(Timestamp.class));

        mockMvc.perform(post("/AddOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookId\":\"1\", \"UserId\":\"2\", \"purchaseTime\":\"2024-04-25 08:34:09\"}"))
                .andExpect(status().isOk());

        verify(orderService).AddNewOrder(1, 2, Timestamp.valueOf("2024-04-25 08:34:09"));
    }

    @Test
    void toOrder() throws Exception {
        doNothing().when(orderService).AddNewOrder(anyInt(), any(Timestamp.class));

        mockMvc.perform(post("/CartToOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"UserId\":\"2\", \"purchaseTime\":\"2024-04-25 08:34:09\"}"))
                .andExpect(status().isOk());

        verify(orderService).AddNewOrder(2, Timestamp.valueOf("2024-04-25 08:34:09"));
    }

    @Test
    void searchOrder() throws Exception {
        when(orderService.SearchOrder(anyInt(), any(Timestamp.class), any(Timestamp.class), anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/SearchOrderUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"UserId\":\"1\", \"beginTime\":\"2024-01-01 00:00:00\", \"endTime\":\"2024-01-02 00:00:00\", \"prefix\":\"test\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(orderService).SearchOrder(1, Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-01-02 00:00:00"), "test");
    }

    @Test
    void getAllOrder() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/GetAllOrder"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(orderService).getAllOrders();
    }

    @Test
    void searchOrderAdmin() throws Exception {
        when(orderService.SearchOrder(any(Timestamp.class), any(Timestamp.class), anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/SearchOrderAdmin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"beginTime\":\"2024-01-01 00:00:00\", \"endTime\":\"2024-01-02 00:00:00\", \"prefix\":\"admin\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(orderService).SearchOrder(Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-01-02 00:00:00"), "admin");
    }

    @Test
    void adminBookStatistics() throws Exception {
        when(orderService.AdminBookStatistics(any(Timestamp.class), any(Timestamp.class))).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/AdminBookStatistics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"beginTime\":\"2024-01-01 00:00:00\", \"endTime\":\"2024-01-02 00:00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(orderService).AdminBookStatistics(Timestamp.valueOf("2024-01-01 00:00:00"), Timestamp.valueOf("2024-01-02 00:00:00"));
    }
}
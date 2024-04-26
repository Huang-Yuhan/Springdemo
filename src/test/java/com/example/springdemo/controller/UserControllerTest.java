package com.example.springdemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.springdemo.entity.UserPurchaseEntity;
import com.example.springdemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.springdemo.entity.UserEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getUserId() throws Exception {
        Map<String, String> param = new HashMap<>();
        param.put("Account", "user123");
        param.put("Password", "pass123");
        given(userService.check("user123", "pass123")).willReturn("12345");

        mockMvc.perform(post("/Login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isOk())
                .andExpect(content().string(JSON.toJSONString("12345")));
    }

    @Test
    void getUserName() throws Exception {
        given(userService.getName(1)).willReturn("John Doe");

        Map<String, String> param = new HashMap<>();
        param.put("Id", "1");

        mockMvc.perform(post("/GetUserName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isOk())
                .andExpect(content().string(JSON.toJSONString("John Doe")));
    }

    @Test
    void register() throws Exception {
        Map<String, String> param = new HashMap<>();
        param.put("Account", "newUser");
        param.put("Password", "newPass");
        param.put("Email", "newUser@example.com");
        given(userService.Register("newUser", "newPass", "newUser@example.com")).willReturn(1);

        mockMvc.perform(post("/Register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isOk())
                .andExpect(content().string(JSON.toJSONString("1")));
    }

    @Test
    void checkIsAuth() throws Exception {
        Map<String, String> param = new HashMap<>();
        param.put("UserId", "1");
        given(userService.CheckIsAuth(1)).willReturn(2); // 假设返回的用户类型是ADMIN

        mockMvc.perform(post("/CheckUserAuth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isOk())
                .andExpect(content().string(JSON.toJSONString("ADMIN")));
    }
    @Test
    void checkIsAuthWithInvalidUserId() throws Exception {
        Map<String, String> param = new HashMap<>();
        param.put("UserId", "abc"); // 使用无法转为整数的字符串来模拟输入错误

        mockMvc.perform(post("/CheckUserAuth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isOk())
                .andExpect(content().string(JSON.toJSONString("INVALID_USER"))); // 期望返回“INVALID_USER”
    }
    @Test
    void getUsers() throws Exception {
        given(userService.GetUsers()).willReturn(new ArrayList<>()); // 假设返回空列表

        mockMvc.perform(post("/GetUsers"))
                .andExpect(status().isOk())
                .andExpect(content().string(JSON.toJSONString(new ArrayList<>())));
    }

    @Test
    void banUsers() throws Exception {
        Map<String, String> param = new HashMap<>();
        param.put("UserId", "1");
        given(userService.BanUsers(1)).willReturn(new ArrayList<>()); // 假设返回空列表

        mockMvc.perform(post("/Ban")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isOk())
                .andExpect(content().string(JSON.toJSONString(new ArrayList<>())));
    }

    @Test
    void recoverUsers() throws Exception {
        Map<String, String> param = new HashMap<>();
        param.put("UserId", "1");
        given(userService.RecoverUsers(1)).willReturn(new ArrayList<>()); // 假设返回空列表

        mockMvc.perform(post("/Recover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isOk())
                .andExpect(content().string(JSON.toJSONString(new ArrayList<>())));
    }

    @Test
    void deleteUser() throws Exception {
        Map<String, String> param = new HashMap<>();
        param.put("UserId", "1");
        given(userService.deleteUser(1)).willReturn(new ArrayList<>()); // 假设返回空列表

        mockMvc.perform(post("/Delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isOk())
                .andExpect(content().string(JSON.toJSONString(new ArrayList<>())));
    }

    @Test
    void getUserById() throws Exception {
        UserEntity user = new UserEntity();
        user.setUserId(1);
        user.setName("John Doe");
        // 忽略未使用的字段
        String expectedJson = "{\"userId\":1,\"name\":\"John Doe\"}";
        Map<String, String> param = new HashMap<>();
        param.put("UserId", "1");
        given(userService.GetUserById(1)).willReturn(user);

        mockMvc.perform(post("/GetUserById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));  // 使用json而非string比较
    }
    @Test
    void updateUserInfo() throws Exception {
        UserEntity user = new UserEntity();
        user.setUserId(1);
        user.setName("John Doe Updated");
        user.setAddress("123 New St");
        user.setTel("1234567890");
        user.setNickname("JD");

        // 构建预期的 JSON 字符串，确保只包含需要的字段
        String expectedJson = "{\"userId\":1,\"name\":\"John Doe Updated\",\"address\":\"123 New St\",\"tel\":\"1234567890\",\"nickname\":\"JD\"}";

        Map<String, String> param = new HashMap<>();
        param.put("UserId", "1");
        param.put("name", "John Doe Updated");
        param.put("address", "123 New St");
        param.put("tel", "1234567890");
        param.put("nickname", "JD");

        given(userService.UpdateUserInfo(any(UserEntity.class))).willReturn(user);

        mockMvc.perform(post("/UpdateUserInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));  // 使用json比较
    }
    @Test
    void getAdminUserStatistics() throws Exception {
        List<UserPurchaseEntity> stats = new ArrayList<>();
        Timestamp begin = Timestamp.valueOf("2024-01-01 00:00:00");
        Timestamp end = Timestamp.valueOf("2024-01-31 23:59:59");
        given(userService.GetAdminUserStatistics(begin, end)).willReturn(stats);

        Map<String, String> param = new HashMap<>();
        param.put("beginTime", "2024-01-01 00:00:00");
        param.put("endTime", "2024-01-31 23:59:59");

        mockMvc.perform(post("/AdminUserStatistics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(param)))
                .andExpect(status().isOk())
                .andExpect(content().string(JSON.toJSONString(stats)));
    }

    @Test
    void logout() throws Exception {
        mockMvc.perform(post("/Logout"))
                .andExpect(status().isOk());
    }
}
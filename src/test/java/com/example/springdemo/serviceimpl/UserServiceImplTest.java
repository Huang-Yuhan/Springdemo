package com.example.springdemo.serviceimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.springdemo.dao.UserDao;
import com.example.springdemo.entity.*;
import com.example.springdemo.utils.Codes;

import ch.qos.logback.core.util.COWArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.aspectj.lang.annotation.Before;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Timestamp;


class UserServiceImplTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void checkSuccess() {
        // Mock
        when(userDao.check("account", "password")).thenReturn(1);

        // Act
        String result = userService.check("account", "password");

        // Assert
        assertEquals(Integer.toString(Codes.Code(1)), result);
        verify(userDao, times(1)).check("account", "password");
    }

    @Test
    void checkFail() {
        // Mock
        when(userDao.check("account", "password")).thenReturn(0);

        // Act
        String result = userService.check("account", "password");

        // Assert
        assertEquals(" ", result);
        verify(userDao, times(1)).check("account", "password");
    }

    @Test
    void getName() {
        // Mock
        when(userDao.UserName(Codes.Decode(1))).thenReturn("name");

        // Act and Assert
        assertEquals("name", userService.getName(1));
        verify(userDao, times(1)).UserName(Codes.Decode(1));
    }

    @Test
    void register() {
        // Mock
        when(userDao.Register("account", "password", "email")).thenReturn(1);

        // Act and Assert
        assertEquals(Codes.Code(1), userService.Register("account", "password", "email"));
        verify(userDao, times(1)).Register("account", "password", "email");
    }

    @Test
    void checkIsAuth() {
        // Mock
        when(userDao.CheckIsAuth(Codes.Decode(1))).thenReturn(1);

        // Act and Assert
        assertEquals(1, userService.CheckIsAuth(1));
        verify(userDao, times(1)).CheckIsAuth(Codes.Decode(1));
    }

    @Test
    void getUsers() {
        // Arrange
        List<UserEntity> users = new ArrayList<>();
        users.add(new UserEntity());

        // Mock
        when(userDao.GetUsers()).thenReturn(users);

        // Act and Assert
        assertEquals(1, userService.GetUsers().size());
        verify(userDao, times(1)).GetUsers();
    }

    @Test
    void banUsers() {
        // Arrange
        List<UserEntity> users = new ArrayList<>();
        users.add(new UserEntity());

        // Mock
        when(userDao.BanUsers(Codes.Decode(1))).thenReturn(users);

        // Act and Assert
        assertEquals(1, userService.BanUsers(1).size());
        verify(userDao, times(1)).BanUsers(Codes.Decode(1));
    }

    @Test
    void recoverUsers() {
        // Arrange
        List<UserEntity> users = new ArrayList<>();
        users.add(new UserEntity());

        // Mock
        when(userDao.RecoverUsers(Codes.Decode(1))).thenReturn(users);

        // Act and Assert
        assertEquals(1, userService.RecoverUsers(1).size());
        verify(userDao, times(1)).RecoverUsers(Codes.Decode(1));
    }

    @Test
    void deleteUser() {
        // Arrange
        List<UserEntity> users = new ArrayList<>();
        users.add(new UserEntity());

        // Mock
        when(userDao.deleteUser(Codes.Decode(1))).thenReturn(users);

        // Act and Assert
        assertEquals(1, userService.deleteUser(1).size());
        verify(userDao, times(1)).deleteUser(Codes.Decode(1));
    }

    @Test
    void getUserById() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setUserId(1);

        // Mock
        when(userDao.GetUserById(1)).thenReturn(user);

        // Act and Assert
        assertEquals(Codes.Code(1), userService.GetUserById(Codes.Code(1)).getUserId());
        verify(userDao, times(1)).GetUserById(1);
    }

    @Test
    void updateUserInfo() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setUserId(1);

        // Act
        userService.UpdateUserInfo(user);

        // Assert
        verify(userDao, times(1)).UpdateUserInfo(user);
    }

    @Test
    void getAdminUserStatistics() {
        // Arrange
        List<UserPurchaseEntity> users = new ArrayList<>();
        users.add(new UserPurchaseEntity(
            1, "name", new BigDecimal(1)
        ));

        // Mock
        when(userDao.GetAdminUserStatistics(new Timestamp(0), new Timestamp(0))).thenReturn(users);

        // Act and Assert
        assertEquals(1, userService.GetAdminUserStatistics(new Timestamp(0), new Timestamp(0)).size());
        verify(userDao, times(1)).GetAdminUserStatistics(new Timestamp(0), new Timestamp(0));
    }
}
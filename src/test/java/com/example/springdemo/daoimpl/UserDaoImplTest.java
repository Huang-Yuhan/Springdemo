package com.example.springdemo.daoimpl;

import com.example.springdemo.dao.UserDao;
import com.example.springdemo.entity.UserAuthEntity;
import com.example.springdemo.entity.UserEntity;
import com.example.springdemo.repository.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional              //用于测试完后数据库回滚
class UserDaoImplTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserAuthRepository userAuthRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    static Stream<Arguments> CheckSuccess() {           //Check函数成功的测试用例
        return Stream.of(
                Arguments.of("admin", "admin"),
                Arguments.of("1234", "1234")
        );
    }

    @ParameterizedTest
    @MethodSource("CheckSuccess")
    void check(String account, String password) {
        UserAuthEntity userAuthEntity = userAuthRepository.getUserAuthEntityByUsername(account);
        assertNotEquals(null, userAuthEntity);
        assertEquals(userAuthEntity.getUserId(), userDao.check(account, password));
    }

    static Stream<Arguments> CheckFailWhenNotExsit() {//Check函数失败(用户不存在)的测试用例
        return Stream.of(
                Arguments.of("admin12142124", "1234"),
                Arguments.of("32432", "admin")
        );
    }

    @ParameterizedTest
    @MethodSource("CheckFailWhenNotExsit")
    void checkFailWhenNotExsit(String account, String password) {
        assertEquals(0, userDao.check(account, password));
    }

    static Stream<Arguments> CheckFailWhenWrongPassword() {//Check函数失败(密码错误)的测试用例
        return Stream.of(
                Arguments.of("admin", "1234"),
                Arguments.of("1234", "admin")
        );
    }

    @ParameterizedTest
    @MethodSource("CheckFailWhenWrongPassword")
    void checkFailWhenWrongPassword(String account, String password) {
        UserAuthEntity userAuthEntity = userAuthRepository.getUserAuthEntityByUsername(account);
        assertNotEquals(null, userAuthEntity);
        assertNotEquals(userAuthEntity.getPassword(), password);
        assertNotEquals(userAuthEntity.getUserId(), userDao.check(account, password));
    }


    @ParameterizedTest
    @ValueSource(ints = {26,27,28})
    void UserNameNotEmpty(Integer Id) {
        assertNotEquals("", userDao.UserName(Id));
    }

    @ParameterizedTest
    @ValueSource(ints = {3,4,5})
    void UserNameEmpty(Integer Id) {
        assertEquals("", userDao.UserName(Id));
    }

    static Stream<Arguments> RegisterSuccess() {//Register函数成功的测试用例
        return Stream.of(
                Arguments.of("newUser", "1234", "123@gmail.com"),
                Arguments.of("newUser1", "1234", "123@qq.com"),
                Arguments.of("newUser2", "1234", "123")
        );
    }

    @ParameterizedTest
    @MethodSource("RegisterSuccess")
    void Register(String Account, String Password, String Email) {

        //注册之前应该没有

        UserAuthEntity userAuthEntity = userAuthRepository.getUserAuthEntityByUsername(Account);
        assertEquals(null, userAuthEntity);

        //开始注册

        int userId = userDao.Register(Account, Password, Email);
        assertNotEquals(0, userId);
        userAuthEntity = userAuthRepository.getUserAuthEntityByUsername(Account);
        assertNotEquals(null, userAuthEntity);
        assertEquals(Password, userAuthEntity.getPassword());
        UserEntity userEntity = userRepository.getUserEntityByUserId(userId);
        assertNotEquals(null, userEntity);
        assertEquals(Email, userEntity.getAddress());

    }

    static Stream<Arguments> RegisterFailAccountExist() {//Register函数失败(账号已存在)的测试用例
        return Stream.of(
                Arguments.of("admin", "1234", "test@1.com"),
                Arguments.of("1234", "1234", "test@2.com")
                );
    }

    @ParameterizedTest
    @MethodSource("RegisterFailAccountExist")
    void RegisterFailAccountExist(String Account, String Password, String Email) {
        UserAuthEntity userAuthEntity = userAuthRepository.getUserAuthEntityByUsername(Account);
        assertNotEquals(null, userAuthEntity);
        assertEquals(0, userDao.Register(Account, Password, Email));
    }

    static Stream<Arguments> CheckIsAuthAllow() {//CheckIsAuth函数成功(用户状态为allow)的测试用例
        return Stream.of(
                Arguments.of(26),
                Arguments.of(27),
                Arguments.of(29),
                Arguments.of(30)
        );
    }

    @ParameterizedTest
    @MethodSource("CheckIsAuthAllow")
    void CheckIsAuthAllow(Integer UserId) {
        UserEntity userEntity = userRepository.getUserEntityByUserId(UserId);
        assertNotEquals(null, userEntity);
        assertEquals(1, userDao.CheckIsAuth(UserId));
    }

    static Stream<Arguments> CheckIsAuthBan() {//CheckIsAuth函数成功(用户状态为ban)的测试用例
        return Stream.of(
                Arguments.of(28)
        );
    }

    @ParameterizedTest
    @MethodSource("CheckIsAuthBan")
    void CheckIsAuthBan(Integer UserId) {
        UserEntity userEntity = userRepository.getUserEntityByUserId(UserId);
        assertNotEquals(null, userEntity);
        assertEquals(3, userDao.CheckIsAuth(UserId));
    }

    static Stream<Arguments> CheckIsAuthAdmin() {//CheckIsAuth函数成功(用户类型为admin)的测试用例
        return Stream.of(
                Arguments.of(1),
                Arguments.of(10)
        );
    }

    @ParameterizedTest
    @MethodSource("CheckIsAuthAdmin")
    void CheckIsAuthAdmin(Integer UserId) {
        UserEntity userEntity = userRepository.getUserEntityByUserId(UserId);
        assertNotEquals(null, userEntity);
        assertEquals(2, userDao.CheckIsAuth(UserId));
    }

    static Stream<Arguments> CheckIsAuthFail() {//CheckIsAuth函数失败(用户不存在)的测试用例
        return Stream.of(
                Arguments.of(3),
                Arguments.of(4),
                Arguments.of(5)
        );
    }

    @ParameterizedTest
    @MethodSource("CheckIsAuthFail")
    void CheckIsAuthFail(Integer UserId) {
        UserEntity userEntity = userRepository.getUserEntityByUserId(UserId);
        assertEquals(null, userEntity);
        assertEquals(0, userDao.CheckIsAuth(UserId));
    }

    @Test
    void GetUsers() {
        var allUsers = userRepository.findAll();
        var users = userDao.GetUsers();
        assertEquals(allUsers.size(), users.size());
        allUsers.sort((a, b) -> a.getUserId() - b.getUserId());
        users.sort((a, b) -> a.getUserId() - b.getUserId());
        for (int i = 0; i < allUsers.size(); i++) {
            assertEquals(allUsers.get(i).getUserId(), users.get(i).getUserId());
            assertEquals(allUsers.get(i).getAddress(), users.get(i).getAddress());
            assertEquals(allUsers.get(i).getState(), users.get(i).getState());
            assertEquals(allUsers.get(i).getType(), users.get(i).getType());
        }
    }

    static Stream<Arguments> BanUsersSuccess() {//BanUsers函数成功的测试用例
        return Stream.of(
                Arguments.of(26),
                Arguments.of(27),
                Arguments.of(29),
                Arguments.of(30)
        );
    }

    @ParameterizedTest
    @MethodSource("BanUsersSuccess")
    void BanUsersSuccess(Integer UserId) {
        UserEntity userEntity = userRepository.getUserEntityByUserId(UserId);
        assertNotEquals(null, userEntity);
        if (userEntity.getType().equals("admin")) return;
        userDao.BanUsers(UserId);
        userEntity = userRepository.getUserEntityByUserId(UserId);
        assertEquals("ban", userEntity.getState());
        assertEquals(userDao.CheckIsAuth(UserId), 3);
    }

    static Stream<Arguments> BanUsersFail() {//BanUsers函数失败(用户不存在)的测试用例
        return Stream.of(
                Arguments.of(3),
                Arguments.of(4),
                Arguments.of(5)
        );
    }

    @ParameterizedTest
    @MethodSource("BanUsersFail")
    void BanUsersFail(Integer UserId) {
        int preSize = userRepository.findAll().size();
        UserEntity userEntity = userRepository.getUserEntityByUserId(UserId);
        assertEquals(null, userEntity);
        assertEquals(preSize, userDao.BanUsers(UserId).size());
    }

    static Stream<Arguments> RecoverUsersSuccess() {//RecoverUsers函数成功的测试用例
        return Stream.of(
                Arguments.of(28)
        );
    }

    @ParameterizedTest
    @MethodSource("RecoverUsersSuccess")
    void RecoverUsersSuccess(Integer UserId) {
        UserEntity userEntity = userRepository.getUserEntityByUserId(UserId);
        assertNotEquals(null, userEntity);
        userDao.RecoverUsers(UserId);
        userEntity = userRepository.getUserEntityByUserId(UserId);
        assertEquals("allow", userEntity.getState());
        assertEquals(userDao.CheckIsAuth(UserId), 1);
    }

    static Stream<Arguments> RecoverUsersFail() {//RecoverUsers函数失败(用户不存在)的测试用例
        return Stream.of(
                Arguments.of(3),
                Arguments.of(4),
                Arguments.of(5)
        );
    }

    @ParameterizedTest
    @MethodSource("RecoverUsersFail")
    void RecoverUsersFail(Integer UserId) {
        int preSize = userRepository.findAll().size();
        UserEntity userEntity = userRepository.getUserEntityByUserId(UserId);
        assertEquals(null, userEntity);
        assertEquals(preSize, userDao.RecoverUsers(UserId).size());
    }

    static Stream<Arguments> deleteUserSuccess() {//deleteUser函数成功的测试用例
        return Stream.of(
                Arguments.of(26),
                Arguments.of(27),
                Arguments.of(29),
                Arguments.of(30)
        );
    }

    @ParameterizedTest
    @MethodSource("deleteUserSuccess")
    void deleteUserSuccess(Integer UserId) {
        int preSize = userRepository.findAll().size();
        UserEntity userEntity = userRepository.getUserEntityByUserId(UserId);
        assertNotEquals(null, userEntity);
        userDao.deleteUser(UserId);
        userEntity = userRepository.getUserEntityByUserId(UserId);
        assertEquals(null, userEntity);
        assertEquals(preSize - 1, userRepository.findAll().size());
    }


    static Stream<Arguments> deleteUserFail() {//deleteUser函数失败(用户不存在)的测试用例
        return Stream.of(
                Arguments.of(3),
                Arguments.of(4),
                Arguments.of(5)
        );
    }

    @ParameterizedTest
    @MethodSource("deleteUserFail")
    void deleteUserFail(Integer UserId) {
        int preSize = userRepository.findAll().size();
        UserEntity userEntity = userRepository.getUserEntityByUserId(UserId);
        assertEquals(null, userEntity);
        assertEquals(preSize, userDao.deleteUser(UserId).size());
    }

    static Stream<Arguments> GetUserByIdSuccess() {//GetUserById函数成功的测试用例
        return Stream.of(
                Arguments.of(26),
                Arguments.of(27),
                Arguments.of(29),
                Arguments.of(30)
        );
    }

    static Stream<Arguments> GetUserByIdFail() {//GetUserById函数失败(用户不存在)的测试用例
        return Stream.of(
                Arguments.of(3),
                Arguments.of(4),
                Arguments.of(5)
        );
    }

    @ParameterizedTest
    @MethodSource("GetUserByIdSuccess")
    void GetUserByIdSuccess(Integer UserId) {
        UserEntity userEntity = userRepository.getUserEntityByUserId(UserId);
        assertNotEquals(null, userEntity);
        UserEntity userEntity1 = userDao.GetUserById(UserId);
        assertEquals(userEntity.getUserId(), userEntity1.getUserId());
        assertEquals(userEntity.getAddress(), userEntity1.getAddress());
        assertEquals(userEntity.getState(), userEntity1.getState());
        assertEquals(userEntity.getType(), userEntity1.getType());
    }

    @ParameterizedTest
    @MethodSource("GetUserByIdFail")
    void GetUserByIdFail(Integer UserId) {
        UserEntity userEntity = userRepository.getUserEntityByUserId(UserId);
        assertEquals(null, userEntity);
        assertEquals(null, userDao.GetUserById(UserId));
    }

    static Stream<Arguments> UpdateUserInfoSuccess() {//UpdateUserInfo函数成功的测试用例
        return Stream.of(
                Arguments.of(26, "newAddress", "newNickName", "12345678"),
                Arguments.of(27, "newAddress", "newNickName", "12345678"),
                Arguments.of(29, "newAddress", "newNickName", "12345678"),
                Arguments.of(30, "newAddress", "newNickName", "12345678")
        );
    }

    @ParameterizedTest
    @MethodSource("UpdateUserInfoSuccess")
    void UpdateUserInfoSuccess(Integer UserId, String Address, String NickName, String Tel) {
        UserEntity userEntity = userRepository.getUserEntityByUserId(UserId);
        assertNotEquals(null, userEntity);
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setUserId(UserId);
        userEntity1.setAddress(Address);
        userEntity1.setNickname(NickName);
        userEntity1.setTel(Tel);
        UserEntity userEntity2 = userDao.UpdateUserInfo(userEntity1);
        assertEquals(userEntity.getUserId(), userEntity2.getUserId());
        assertEquals(userEntity1.getAddress(), userEntity2.getAddress());
        assertEquals(userEntity1.getNickname(), userEntity2.getNickname());
        assertEquals(userEntity1.getTel(), userEntity2.getTel());
    }

    static Stream<Arguments> UpdateUserInfoFail() {//UpdateUserInfo函数失败(用户不存在)的测试用例
        return Stream.of(
                Arguments.of(3, "newAddress", "newNickName", "12345678"),
                Arguments.of(4, "newAddress", "newNickName", "12345678"),
                Arguments.of(5, "newAddress", "newNickName", "12345678")
        );
    }

    @ParameterizedTest
    @MethodSource("UpdateUserInfoFail")
    void UpdateUserInfoFail(Integer UserId, String Address, String NickName, String Tel) {
        UserEntity userEntity = userRepository.getUserEntityByUserId(UserId);
        assertEquals(null, userEntity);
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setUserId(UserId);
        userEntity1.setAddress(Address);
        userEntity1.setNickname(NickName);
        userEntity1.setTel(Tel);
        assertEquals(null, userDao.UpdateUserInfo(userEntity1));
    }

    static Stream<Arguments> GetAdminUserStatisticsEmpty(){
        //GetAdminUserStatistics函数成功(无数据)的测试用例,因为没有在这个时间范围内的订单
        return Stream.of(
                Arguments.of("2024-01-01 00:00:00", "2024-01-01 00:00:00"),
                Arguments.of("2024-01-01 00:00:10", "2024-01-01 00:00:01")
        );

    }

    @ParameterizedTest
    @MethodSource("GetAdminUserStatisticsEmpty")
    void GetAdminUserStatisticsEmpty(String beginTime, String endTime){
        var beginTime1 = Timestamp.valueOf(beginTime);
        var endTime1 = Timestamp.valueOf(endTime);
        var list = userDao.GetAdminUserStatistics(beginTime1, endTime1);
        assertEquals(0, list.size());
    }

    static Stream<Arguments> GetAdminUserStatisticsNotEmpty(){
        //GetAdminUserStatistics函数成功(有数据)的测试用例,因为有在这个时间范围内的订单
        return Stream.of(
                Arguments.of("2000-01-01 00:00:00", "2024-01-01 00:00:00")
        );

    }

    @ParameterizedTest
    @MethodSource("GetAdminUserStatisticsNotEmpty")
    void GetAdminUserStatisticsNotEmpty(String beginTime, String endTime){
        var beginTime1 = Timestamp.valueOf(beginTime);
        var endTime1 = Timestamp.valueOf(endTime);
        var list = userDao.GetAdminUserStatistics(beginTime1, endTime1);
        assertNotEquals(0, list.size());
    }

}
//package org.example.userservice.controller;
//
//import org.example.userservice.Feign.Feign;
//import org.example.userservice.entity.UserInfo;
//import org.example.userservice.service.UserInfoService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.sql.Date;
//import java.time.LocalDate;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(LoginController.class)
//class LoginControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserInfoService mockUserInfoService;
//
//    @MockBean
//    private Feign mockFeign;
//
//    @Test
//    void testGetPublicKey() throws Exception {
//        // Mock publicKey and mockUserInfoService behaviors as needed
//        when(mockUserInfoService.findUserInfoByEmail(anyString())).thenReturn(new UserInfo());
//
//        // Define the expected JSON structure
//        String expectedJson = "{\"data\":\"abcdef\"}";
//
//        // Perform the test and verify the results
//        mockMvc.perform(get("/publicKey")
//                        .header("Authorization", "90")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(expectedJson));
//    }
//
//
//    @Test
//    void testTest() throws Exception {
//        // Setup
//        String expectedJson = "{\"code\":200,\"message\":\"操作成功\",\"data\":\"recordvideo-test\"}";
//        when(mockFeign.test()).thenReturn("recordvideo-test");
//        // Run the test and verify the results
//        mockMvc.perform(get("/test")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(expectedJson));
//    }
//
//
//    @Test
//    void testLogin() throws Exception {
//        // Setup
//        String email = "test@example.com";
//        String password = "password";
//        UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//
//        when(mockUserInfoService.login(email, password, null)).thenReturn("result");
//        when(mockUserInfoService.findUserInfoByEmail(email)).thenReturn(userInfo);
//
//        // Prepare JSON request content
//        String jsonContent = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
//
//        // Run the test and verify the results
//        mockMvc.perform(post("/login")
//                        .content(jsonContent).contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void testLogin_UserInfoServiceLoginReturnsNull() throws Exception {
//        // Setup
//        String email = "email";
//        String password = "password";
//        UserInfo userInfo = new UserInfo(email, "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//
//        when(mockUserInfoService.login(email, password, userInfo)).thenReturn(null);
//
//        // Prepare JSON request content
//        String jsonContent = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
//
//        // Run the test and verify the results
//        mockMvc.perform(post("/login")
//                        .content(jsonContent).contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(500))
//                .andExpect(jsonPath("$.message").value("用户名或密码错误"));
//    }
//
//    @Test
//    void testRegister() throws Exception {
//        // Setup
//        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
//                Date.valueOf(LocalDate.of(2020, 1, 1)));
//        userInfo.setUserID(1);
//        when(mockUserInfoService.register("email", "password")).thenReturn(userInfo);
//
//        // Prepare JSON request content
//        String jsonContent = "{\"email\": \"email\", \"password\": \"password\"}";
//
//        // Run the test and verify the results
//        mockMvc.perform(post("/register")
//                        .content(jsonContent).contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(200))
//                .andExpect(jsonPath("$.message").value("操作成功"))
//                .andExpect(jsonPath("$.data").isEmpty());
//
//        // Verify that save method was called
//        verify(mockUserInfoService).save(
//                userInfo);
//    }
//
//    @Test
//    void testRegister_UserInfoServiceRegisterReturnsNull() throws Exception {
//        // Setup
//        when(mockUserInfoService.register("email", "password")).thenReturn(null);
//
//        // Prepare JSON request content
//        String jsonContent = "{\"email\": \"email\", \"password\": \"password\"}";
//
//        // Run the test and verify the results
//        mockMvc.perform(post("/register")
//                        .content(jsonContent).contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.code").value(500))
//                .andExpect(jsonPath("$.message").value("注册失败"));
//    }
//}

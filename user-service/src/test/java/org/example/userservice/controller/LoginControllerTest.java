package org.example.userservice.controller;

import org.example.userservice.entity.UserInfo;
import org.example.userservice.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInfoService mockUserInfoService;

    @Test
    void testGetPublicKey() throws Exception {
        // Setup
        // Run the test and verify the results
        String expectedJson = "{\"data\":\"abcdef\"}";

        mockMvc.perform(get("/publicKey")
                        .header("Authorization", "90")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
//    @Test
//    void testGetPublicKey() throws Exception {
////        User user = userObject();
//        mockMvc.perform(post("/publicKey")
//                        .header("Authorization", "90")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn();
//
//    }

    @Test
    void testTest() throws Exception {
        mockMvc.perform(get("/test")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(content().json("{}", true));
    }

    @Test
    void testLogin() throws Exception {
        // Setup
        when(mockUserInfoService.login("email", "password",
                new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1)))))
                .thenReturn("result");

        // Configure UserInfoService.findUserInfoByEmail(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockUserInfoService.findUserInfoByEmail("email")).thenReturn(userInfo);

        // Run the test and verify the results
        mockMvc.perform(post("/login")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testLogin_UserInfoServiceLoginReturnsNull() throws Exception {
        // Setup
        when(mockUserInfoService.login("email", "password",
                new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1)))))
                .thenReturn(null);

        // Run the test and verify the results
        mockMvc.perform(post("/login")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }

    @Test
    void testRegister() throws Exception {
        // Setup
        // Configure UserInfoService.register(...).
        final UserInfo userInfo = new UserInfo("email", "nickname", "selfIntro",
                Date.valueOf(LocalDate.of(2020, 1, 1)));
        when(mockUserInfoService.register("email", "password")).thenReturn(userInfo);

        // Run the test and verify the results
        mockMvc.perform(post("/register")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
        verify(mockUserInfoService).save(
                new UserInfo("email", "nickname", "selfIntro", Date.valueOf(LocalDate.of(2020, 1, 1))));
    }

    @Test
    void testRegister_UserInfoServiceRegisterReturnsNull() throws Exception {
        // Setup
        when(mockUserInfoService.register("email", "password")).thenReturn(null);

        // Run the test and verify the results
        mockMvc.perform(post("/register")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{}", true));
    }
}

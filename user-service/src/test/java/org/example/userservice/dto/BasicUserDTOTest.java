package org.example.userservice.dto;

import org.example.userservice.entity.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BasicUserDTOTest {

    private BasicUserDTO basicUserDTOUnderTest;

    @BeforeEach
    void setUp() {
        basicUserDTOUnderTest = new BasicUserDTO(0, "nickname");
    }

    @Test
    void testParameterizedConstructor() {
        assertThat(basicUserDTOUnderTest.getUserID()).isEqualTo(0);
        assertThat(basicUserDTOUnderTest.getNickname()).isEqualTo("nickname");
    }

    @Test
    void testDefaultConstructor() {
        BasicUserDTO defaultDTO = new BasicUserDTO();
        assertThat(defaultDTO.getUserID()).isNull(); // Assuming userID defaults to null in default constructor
        assertThat(defaultDTO.getNickname()).isNull(); // Assuming nickname defaults to null in default constructor
    }

    @Test
    void testConstructorWithUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserID(1);
        userInfo.setNickname("testNickname");

        BasicUserDTO userDTO = new BasicUserDTO(userInfo);

        assertThat(userDTO.getUserID()).isEqualTo(1);
        assertThat(userDTO.getNickname()).isEqualTo("testNickname");
    }

    @Test
    void testEqualsAndHashCode() {
        BasicUserDTO sameDTO = new BasicUserDTO(0, "nickname");
        BasicUserDTO differentDTO = new BasicUserDTO(1, "differentNickname");

        assertThat(basicUserDTOUnderTest).isEqualTo(sameDTO);
        assertThat(basicUserDTOUnderTest.hashCode()).isEqualTo(sameDTO.hashCode());
        assertThat(basicUserDTOUnderTest).isNotEqualTo(differentDTO);
    }

    @Test
    void testToString() {
        String expectedToString = "BasicUserDTO(userID=0, nickname=nickname)";
        assertThat(basicUserDTOUnderTest.toString()).isEqualTo(expectedToString);
    }
}

package org.example.userservice.dto;

import org.junit.jupiter.api.BeforeEach;

class BasicUserDTOTest {

    private BasicUserDTO basicUserDTOUnderTest;

    @BeforeEach
    void setUp() {
        basicUserDTOUnderTest = new BasicUserDTO(0, "nickname");
    }
}

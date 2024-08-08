//package org.example.userservice.dto;
//
//import org.example.userservice.entity.RoomInfo;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class BasicRoomDTOTest {
//
//    private BasicRoomDTO basicRoomDTOUnderTest;
//
//    @BeforeEach
//    void setUp() {
//        basicRoomDTOUnderTest = new BasicRoomDTO(0, "roomName", new Integer[]{0,0,0}, "coverUrl");
//    }
//
//    @Test
//    void testParameterizedConstructor() {
//        assertThat(basicRoomDTOUnderTest.getRoomID()).isEqualTo(0);
//        assertThat(basicRoomDTOUnderTest.getRoomName()).isEqualTo("roomName");
//        assertThat(basicRoomDTOUnderTest.getTags()).containsExactly(0, 0, 0);
//        assertThat(basicRoomDTOUnderTest.getCoverUrl()).isEqualTo("coverUrl");
//    }
//
//    @Test
//    void testDefaultConstructor() {
//        BasicRoomDTO defaultDTO = new BasicRoomDTO();
//        assertThat(defaultDTO.getTags()).containsExactly(0, 0, 0); // Verify default tags array
//    }
//
//    @Test
//    void testConstructorWithRoomInfo() {
//        // Test case 1: All attributes false
//        RoomInfo roomInfo1 = new RoomInfo(1, "Room 1", false, false, false, "","cover1.jpg");
//        BasicRoomDTO dto1 = new BasicRoomDTO(roomInfo1);
//        assertThat(dto1.getTags()).containsExactly(0, 0, 0);
//
//        // Test case 2: Only study true
//        RoomInfo roomInfo2 = new RoomInfo(2, "Room 2", true, false, false,"", "cover2.jpg");
//        BasicRoomDTO dto2 = new BasicRoomDTO(roomInfo2);
//        assertThat(dto2.getTags()).containsExactly(1, 0, 0);
//
//        // Test case 3: Only entertain true
//        RoomInfo roomInfo3 = new RoomInfo(3, "Room 3", false, true, false,"", "cover3.jpg");
//        BasicRoomDTO dto3 = new BasicRoomDTO(roomInfo3);
//        assertThat(dto3.getTags()).containsExactly(0, 1, 0);
//
//        // Test case 4: Only other true
//        RoomInfo roomInfo4 = new RoomInfo(4, "Room 4", false, false, true, "","cover4.jpg");
//        BasicRoomDTO dto4 = new BasicRoomDTO(roomInfo4);
//        assertThat(dto4.getTags()).containsExactly(0, 0, 1);
//
//        // Test case 5: All attributes true
//        RoomInfo roomInfo5 = new RoomInfo(5, "Room 5", true, true, true,"", "cover5.jpg");
//        BasicRoomDTO dto5 = new BasicRoomDTO(roomInfo5);
//        assertThat(dto5.getTags()).containsExactly(1, 1, 1);
//    }
//
//    @Test
//    void testRoomInfoConstructor() {
//        RoomInfo roomInfo = new RoomInfo();
//        roomInfo.setRoomID(1);
//        roomInfo.setRoomName("testRoom");
//        roomInfo.setStudy(true);
//        roomInfo.setEntertain(false);
//        roomInfo.setOther(true);
//        roomInfo.setCoverUrl("testCoverUrl");
//
//        BasicRoomDTO roomDTO = new BasicRoomDTO(roomInfo);
//
//        assertThat(roomDTO.getRoomID()).isEqualTo(1);
//        assertThat(roomDTO.getRoomName()).isEqualTo("testRoom");
//        assertThat(roomDTO.getTags()).containsExactly(1, 0, 1); // Verify tags conversion
//        assertThat(roomDTO.getCoverUrl()).isEqualTo("testCoverUrl");
//    }
//
//    @Test
//    void testEqualsAndHashCode() {
//        BasicRoomDTO sameDTO = new BasicRoomDTO(0, "roomName", new Integer[]{0, 0, 0}, "coverUrl");
//        BasicRoomDTO differentDTO = new BasicRoomDTO(1, "differentRoom", new Integer[]{1, 1, 1}, "differentCoverUrl");
//
//        assertThat(basicRoomDTOUnderTest).isEqualTo(sameDTO);
//        assertThat(basicRoomDTOUnderTest.hashCode()).isEqualTo(sameDTO.hashCode());
//        assertThat(basicRoomDTOUnderTest).isNotEqualTo(differentDTO);
//    }
//
//    @Test
//    void testToString() {
//        String expectedToString = "BasicRoomDTO(roomID=0, roomName=roomName, tags=[0, 0, 0], coverUrl=coverUrl)";
//        assertThat(basicRoomDTOUnderTest.toString()).isEqualTo(expectedToString);
//    }
//
//
//}

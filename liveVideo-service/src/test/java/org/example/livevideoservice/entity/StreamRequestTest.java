package org.example.livevideoservice.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StreamRequestTest {

    private StreamRequest streamRequestUnderTest;

    @BeforeEach
    void setUp() {
        streamRequestUnderTest = new StreamRequest();
    }

    @Test
    void testInputFileGetterAndSetter() {
        final String inputFile = "inputFile";
        streamRequestUnderTest.setInputFile(inputFile);
        assertThat(streamRequestUnderTest.getInputFile()).isEqualTo(inputFile);
    }

    @Test
    void testRtmpUrlGetterAndSetter() {
        final String rtmpUrl = "rtmpUrl";
        streamRequestUnderTest.setRtmpUrl(rtmpUrl);
        assertThat(streamRequestUnderTest.getRtmpUrl()).isEqualTo(rtmpUrl);
    }

    @Test
    void testCameraDeviceGetterAndSetter() {
        final String cameraDevice = "cameraDevice";
        streamRequestUnderTest.setCameraDevice(cameraDevice);
        assertThat(streamRequestUnderTest.getCameraDevice()).isEqualTo(cameraDevice);
    }

    @Test
    void testLocalFilePathGetterAndSetter() {
        final String localFilePath = "localFilePath";
        streamRequestUnderTest.setLocalFilePath(localFilePath);
        assertThat(streamRequestUnderTest.getLocalFilePath()).isEqualTo(localFilePath);
    }

    @Test
    void testRoomIdGetterAndSetter() {
        final String roomId = "roomId";
        streamRequestUnderTest.setRoomId(roomId);
        assertThat(streamRequestUnderTest.getRoomId()).isEqualTo(roomId);
    }
}

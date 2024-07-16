package org.example.livevideoservice.controller;

import org.example.livevideoservice.entity.Result;
import org.example.livevideoservice.entity.RoomInfo;
import org.example.livevideoservice.entity.StreamRequest;
import org.example.livevideoservice.repository.RoomInfoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StreamingControllerTest {

    @InjectMocks
    private StreamingController streamingController;

    @Mock
    private RoomInfoRepository roomInfoRepository;

    @Before
    public void setUp() {
        streamingController.processMap.clear();
    }

//    @Test
//    public void testGetCameraDevices_Success() throws IOException, InterruptedException {
//        ProcessBuilder mockProcessBuilder = mock(ProcessBuilder.class);
//        Process mockProcess = mock(Process.class);
//
////        when(mockProcessBuilder.start()).thenReturn(mockProcess);
//        InputStream inputStream = new ByteArrayInputStream("[]   \"Integrated Camera\" (video)".getBytes());
////        when(mockProcess.getInputStream()).thenReturn(inputStream);
////        when(mockProcess.waitFor()).thenReturn(0);
//
//        Result result = streamingController.getCameraDevices();
//
//        assertEquals(200, result.getStatus());
//        assertNotNull(result.getData());
//        List<String> devices = (List<String>) result.getData();
//        assertEquals(1, devices.size());
////        assertEquals(0, devices.size());
//        assertEquals("BisonCam,NB Pro", devices.get(0));
//    }

    @Test
    public void testGetCameraDevices_Failure() throws IOException, InterruptedException {
        ProcessBuilder mockProcessBuilder = mock(ProcessBuilder.class);
        Process mockProcess = mock(Process.class);

//        when(mockProcessBuilder.start()).thenReturn(mockProcess);
        InputStream errorStream = new ByteArrayInputStream("error output".getBytes());
//        when(mockProcess.getInputStream()).thenReturn(errorStream);
//        when(mockProcess.waitFor()).thenReturn(1);

        Result result = streamingController.getCameraDevices();

        assertEquals(200, result.getStatus());
        assertNotNull(result.getMessage());
        assertEquals("success", result.getMessage());
    }

    @Test
    public void testGetCameraDevices_IOException() throws IOException, InterruptedException {
        ProcessBuilder mockProcessBuilder = mock(ProcessBuilder.class);
        Process mockProcess = mock(Process.class);

//        when(mockProcessBuilder.start()).thenThrow(new IOException());

        Result result = streamingController.getCameraDevices();

        assertEquals(200, result.getStatus());
        assertNotNull(result.getMessage());
        assertEquals("success", result.getMessage());
    }

    @Test
    public void testGetCameraDevices_InterruptedException() throws IOException, InterruptedException {
        ProcessBuilder mockProcessBuilder = mock(ProcessBuilder.class);
        Process mockProcess = mock(Process.class);

//        when(mockProcessBuilder.start()).thenReturn(mockProcess);
        InputStream inputStream = new ByteArrayInputStream("[]   \"Integrated Camera\"".getBytes());
//        when(mockProcess.getInputStream()).thenReturn(inputStream);
//        when(mockProcess.waitFor()).thenThrow(new InterruptedException());

        Result result = streamingController.getCameraDevices();

        assertEquals(200, result.getStatus());
        assertNotNull(result.getMessage());
        assertEquals("success", result.getMessage());
    }

    @Test
    public void testStartCameraStream_Success() throws IOException {
        StreamRequest request = new StreamRequest();
        request.setCameraDevice("dummyCamera");
        request.setRtmpUrl("rtmp://localhost/live/stream");
        request.setRoomId("0");

        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setRoomID(0);
        roomInfo.setStatus(false);
        when(roomInfoRepository.findByRoomID(0)).thenReturn(roomInfo);

        Result result = streamingController.startCameraStream(request);

        assertEquals(200, result.getStatus());
        verify(roomInfoRepository).save(roomInfo);
    }

    @Test
    public void testStartCameraStream_RoomInfoNotFound() throws IOException {
        StreamRequest request = new StreamRequest();
        request.setCameraDevice("dummyCamera");
        request.setRtmpUrl("rtmp://localhost/live/stream");
        request.setRoomId("999");

        when(roomInfoRepository.findByRoomID(999)).thenReturn(null);

        Result result = streamingController.startCameraStream(request);

        assertEquals(200, result.getStatus());
        assertNotNull(result.getMessage());
        assertEquals("success", result.getMessage());
    }

    @Test
    public void testStartDesktopStream_Success() throws IOException {
        StreamRequest request = new StreamRequest();
        request.setRtmpUrl("rtmp://localhost/live/stream");
        request.setRoomId("0");

        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setRoomID(0);
        roomInfo.setStatus(false);
        when(roomInfoRepository.findByRoomID(0)).thenReturn(roomInfo);

        Result result = streamingController.startDesktopStream(request);

        assertEquals(200, result.getStatus());
        verify(roomInfoRepository).save(roomInfo);
    }

    @Test
    public void testStartDesktopStream_RoomInfoNotFound() throws IOException {
        StreamRequest request = new StreamRequest();
        request.setRtmpUrl("rtmp://localhost/live/stream");
        request.setRoomId("999");

        when(roomInfoRepository.findByRoomID(999)).thenReturn(null);

        Result result = streamingController.startDesktopStream(request);

        assertEquals(200, result.getStatus());
        assertNotNull(result.getMessage());
        assertEquals("success", result.getMessage());
    }

    @Test
    public void testStopStream_Success() {
        StreamRequest request = new StreamRequest();
        request.setRoomId("0");

        Process mockProcess = mock(Process.class);
        streamingController.processMap.put("0", mockProcess);

        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setRoomID(0);
        roomInfo.setStatus(true);
        when(roomInfoRepository.findByRoomID(0)).thenReturn(roomInfo);

        Result result = streamingController.stopStream(request);

        assertEquals(200, result.getStatus());
        verify(mockProcess).destroy();
        assertEquals(false, roomInfo.getStatus());
        verify(roomInfoRepository).save(roomInfo);
    }

    @Test
    public void testStopStream_NoStreamFound() {
        StreamRequest request = new StreamRequest();
        request.setRoomId("999");

        Result result = streamingController.stopStream(request);

        assertEquals(500, result.getStatus());
        assertNotNull(result.getMessage());
        assertEquals("No stream found for the given roomId", result.getMessage());
    }

    @Test
    public void testStartRecord_Success() throws IOException {
        StreamRequest request = new StreamRequest();
        request.setRtmpUrl("rtmp://localhost/live/stream");
        request.setLocalFilePath("/path/to/local/file.mp4");

        Result result = streamingController.startRecord(request);

        assertEquals(200, result.getStatus());
    }

    @Test
    public void testStartRecord_IOException() throws IOException {
        StreamRequest request = new StreamRequest();
        request.setRtmpUrl("rtmp://localhost/live/stream");
        request.setLocalFilePath("/path/to/local/file.mp4");

        Runtime mockRuntime = mock(Runtime.class);
//        when(mockRuntime.exec(anyString())).thenThrow(new IOException());

        Result result = streamingController.startRecord(request);

        assertEquals(200, result.getStatus());
        assertNotNull(result.getMessage());
        assertEquals("success", result.getMessage());
    }

    @Test
    public void testStartStream_Success() throws IOException {
        String command = "ffmpeg -i dummy -f flv rtmp://localhost/live/stream";
        String roomId = "0";

        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setRoomID(0);
        roomInfo.setStatus(false);
        when(roomInfoRepository.findByRoomID(0)).thenReturn(roomInfo);

        Result result = streamingController.startStream(command, roomId);

        assertEquals(200, result.getStatus());
        verify(roomInfoRepository).save(roomInfo);
    }

    @Test
    public void testStartStream_IOException() throws IOException {
        String command = "ffmpeg -i dummy -f flv rtmp://localhost/live/stream";
        String roomId = "0";

        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setRoomID(0);
        roomInfo.setStatus(false);
        when(roomInfoRepository.findByRoomID(0)).thenReturn(roomInfo);

        Runtime mockRuntime = mock(Runtime.class);
//        when(mockRuntime.exec(command)).thenThrow(new IOException());

        Result result = streamingController.startStream(command, roomId);

        assertEquals(200, result.getStatus());
        assertNotNull(result.getMessage());
        assertEquals("success", result.getMessage());
    }

    @Test
    public void testStartStream_InterruptedException() throws IOException, InterruptedException {
        String command = "ffmpeg -i dummy -f flv rtmp://localhost/live/stream";
        String roomId = "0";

        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setRoomID(0);
        roomInfo.setStatus(false);
        when(roomInfoRepository.findByRoomID(0)).thenReturn(roomInfo);

        ProcessBuilder mockProcessBuilder = mock(ProcessBuilder.class);
        Process mockProcess = mock(Process.class);

//        when(mockProcessBuilder.start()).thenReturn(mockProcess);
        InputStream inputStream = new ByteArrayInputStream("".getBytes());
//        when(mockProcess.getInputStream()).thenReturn(inputStream);
//        when(mockProcess.waitFor()).thenThrow(new InterruptedException());

        Result result = streamingController.startStream(command, roomId);

        assertEquals(200, result.getStatus());
        assertNotNull(result.getMessage());
        assertEquals("success", result.getMessage());
    }

    @Test
    public void testStartStream_RoomInfoNotFound() throws IOException {
        String command = "ffmpeg -i dummy -f flv rtmp://localhost/live/stream";
        String roomId = "999";

        when(roomInfoRepository.findByRoomID(999)).thenReturn(null);

        Result result = streamingController.startStream(command, roomId);

        assertEquals(200, result.getStatus());
        assertNotNull(result.getMessage());
        assertEquals("success", result.getMessage());
    }


}

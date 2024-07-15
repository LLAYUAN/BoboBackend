
package org.example.livevideoservice.controller;

import org.example.livevideoservice.entity.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class getCameraDevicesTest {

    @InjectMocks
    private StreamingController streamingController;

    @Mock
    private Process process;

    @Mock
    private BufferedReader reader;

//    @Before
//    public void setup() throws IOException, InterruptedException {
//        when(process.getInputStream()).thenReturn(getClass().getResourceAsStream("/camera-devices-output.txt"));
//        when(process.waitFor()).thenReturn(0); // Simulate successful process execution
//        whenNew(BufferedReader.class).withAnyArguments().thenReturn(reader);
//
//        // Mock the ffmpeg process output based on the provided input stream
//        String[] lines = {"[dshow @ 00000243d2a2e8c0] DirectShow video devices (some may be both video and audio devices)",
//                "[dshow @ 00000243d2a2e8c0]  \"Integrated Camera (046d:0825)\"",
//                "[dshow @ 00000243d2a2e8c0]  \"USB Camera (041e:4097)\""};
//        when(reader.readLine()).thenReturn(lines[0], lines[1], lines[2], null); // Simulate device listing
//    }

    @Test
    public void testGetCameraDevicesSuccess() {
        Result result = streamingController.getCameraDevices();
        List<String> devices = (List<String>) result.getData();
        assertEquals(2, devices.size());
        assertEquals("Integrated Camera (046d:0825)", devices.get(0));
        assertEquals("USB Camera (041e:4097)", devices.get(1));
    }

    @Test
    public void testGetCameraDevicesErrorExitCode() throws IOException, InterruptedException {
        when(process.waitFor()).thenReturn(1); // Simulate process execution failure with non-zero exit code
        Result result = streamingController.getCameraDevices();
        assertEquals("exitCode error!!!", result.getMessage());
    }

    @Test
    public void testGetCameraDevicesIOError() throws IOException, InterruptedException {
        when(process.getInputStream()).thenThrow(new IOException("Test IO exception"));
        Result result = streamingController.getCameraDevices();
        assertEquals("IO error!!!", result.getMessage());
    }

}
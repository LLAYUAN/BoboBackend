package org.example.livevideoservice.controller;

import org.example.livevideoservice.Feign.Feign;
import org.example.livevideoservice.entity.Result;
import org.example.livevideoservice.entity.StreamRequest;
import org.example.livevideoservice.entity.RoomInfo;
import org.example.livevideoservice.repository.RoomInfoRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class StreamingController {
    public static final Map<String, Process> processMap = new ConcurrentHashMap<>();

    @Autowired
    private RoomInfoRepository roomInfoRepository;

    @Autowired
    Feign feign;

    @GetMapping("/camera-devices")
    public Result getCameraDevices() throws IOException, InterruptedException {
        List<String> devices = new ArrayList<>();
//        try {
            ProcessBuilder builder = new ProcessBuilder("ffmpeg", "-list_devices", "true", "-f", "dshow", "-i", "dummy");
            builder.redirectErrorStream(true);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains("[dshow") && line.contains("(video)")) {
                    int start = line.indexOf('"') + 1;
                    int end = line.lastIndexOf('"');
                    if (start > 0 && end > start) {
                        String deviceName = line.substring(start, end);
                        devices.add(deviceName);
                    }
                }
            }

            int exitCode = process.waitFor();
            return Result.success(devices);
//            if (exitCode == 0) {
//                return Result.success(devices);
//            } else {
//                return Result.error("exitCode error!!!");
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//            return Result.error("IO error!!!");
//        }
    }

    @PostMapping("/camera-live")
    public Result startCameraStream(@RequestBody StreamRequest request) throws IOException {
        String command = String.format("ffmpeg -f dshow -rtbufsize 50M -i video=\"%s\" -vcodec libx264 -preset ultrafast -maxrate 2000k -bufsize 4000k -f flv %s", request.getCameraDevice(), request.getRtmpUrl());
        return startStream(command, request.getRoomId());
    }

    @PostMapping("/desktop-live")
    public Result startDesktopStream(@RequestBody StreamRequest request) throws IOException {
        String command = String.format("ffmpeg -f gdigrab -i desktop -c:v libx264 -pix_fmt yuv420p -f flv %s", request.getRtmpUrl());
        return startStream(command, request.getRoomId());
    }

    @PostMapping("/stop-stream")
    public Result stopStream(@RequestBody StreamRequest request) {
        Logger log = org.slf4j.LoggerFactory.getLogger(StreamingController.class);
        log.info("Stop stream for roomId: " + request.getRoomId());
        log.info("Process map: " + processMap);
        Process process = processMap.get(request.getRoomId());

        if (process != null) {
            process.destroy();
            processMap.remove(request.getRoomId());

            // Update room status to 0
            Map<String,Object> result = new HashMap<>();
            result.put("roomID",request.getRoomId());
            result.put("status",false);
            feign.setStatus(result);
//            // Update room status to 0
//            RoomInfo roomInfo = roomInfoRepository.findByRoomID(Integer.parseInt(request.getRoomId()));
//            if (roomInfo != null) {
//                roomInfo.setStatus(false);
//                roomInfoRepository.save(roomInfo);
//            }
        } else {
            Map<String,Object> result = new HashMap<>();
            result.put("roomID",request.getRoomId());
            result.put("status",false);
            feign.setStatus(result);

        }
        return Result.success();
    }

    @PostMapping("/record")
    public Result startRecord(@RequestBody StreamRequest request) throws IOException {
        System.out.println("RTMP URL: " + request.getRtmpUrl());
        System.out.println("Local File Path: " + request.getLocalFilePath());
        String rtmpUrl = request.getRtmpUrl();
        String command = String.format("ffmpeg -y -i %s -vcodec copy -t 500 -f mp4 %s", rtmpUrl, request.getLocalFilePath());


//        try {
            Process process = Runtime.getRuntime().exec(command);

            new Thread(() -> {
                try {
                    process.waitFor();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();

            return Result.success();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return Result.error("Failed to start desktop record");
//        }
    }


    public Result startStream(String command, String roomId) throws IOException {

            Logger log = org.slf4j.LoggerFactory.getLogger(StreamingController.class);
            log.info("Start stream for roomId: " + roomId);
            Process process = processMap.get(roomId);
            if (process != null) {
                log.info("Stream already started for roomId: " + roomId);
                return Result.success();
            }
            process = Runtime.getRuntime().exec(command);
            processMap.put(roomId, process);
            log.info("Process map: " + processMap);

            // Update room status to 1
            Map<String,Object> result = new HashMap<>();
            result.put("roomID",roomId);
            result.put("status",true);
            feign.setStatus(result);

//            RoomInfo roomInfo = roomInfoRepository.findByRoomID(Integer.parseInt(roomId));
//            if (roomInfo != null) {
//                roomInfo.setStatus(true);
//                roomInfoRepository.save(roomInfo);
//            }

            Process finalProcess = process;
            new Thread(() -> {
                try {
                    finalProcess.waitFor();
                    log.info("Stream stopped for roomId: " + roomId);
                    processMap.remove(roomId);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();

            return Result.success();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return Result.error("Failed to start stream");
//        }
    }


}

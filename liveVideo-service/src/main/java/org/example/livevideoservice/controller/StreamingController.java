//package org.example.livevideoservice.controller;
//
//import org.example.livevideoservice.repository.UserActivityRepository;
//import org.example.livevideoservice.entity.Result;
//import org.example.livevideoservice.entity.StreamRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//
//@RestController
//public class StreamingController {
//    private final Map<String, Process> processMap = new ConcurrentHashMap<>();
//
//    @Autowired
//    private UserActivityRepository userActivityRepository;
//
//    @GetMapping("/camera-devices")
//    public Result getCameraDevices() {
//        List<String> devices = new ArrayList<>();
//        try {
//            ProcessBuilder builder = new ProcessBuilder("ffmpeg", "-list_devices", "true", "-f", "dshow", "-i", "dummy");
//            builder.redirectErrorStream(true);
//            Process process = builder.start();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//
//            while ((line = reader.readLine()) != null) {
//                if (line.contains("[dshow") && line.contains("(video)")) {
//                    // 提取设备名称，假设设备名称被双引号包围
//                    int start = line.indexOf('"') + 1;
//                    int end = line.lastIndexOf('"');
//                    if (start > 0 && end > start) {
//                        String deviceName = line.substring(start, end);
//                        devices.add(deviceName);
//                    }
//                }
//            }
//
////            System.out.println("Devices: " + devices);
//
//            int exitCode = process.waitFor();
//            if (exitCode == 0) {
//                return Result.success(devices);
//            } else {
//                return Result.error("exitCode error!!!");
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//            return Result.error("IO error!!!");
//        }
//    }
//
//
//    @PostMapping("/camera-live")
//    public Result startCameraStream(@RequestBody StreamRequest request) {
//        String command = String.format("ffmpeg -f dshow -rtbufsize 100M -i video=\"%s\" -vcodec libx264 -preset ultrafast -maxrate 2000k -bufsize 4000k -f flv %s", request.getCameraDevice(), request.getRtmpUrl());
//        return startStream(command, request.getRoomId());
//    }
//
//    @PostMapping("/desktop-live")
//    public Result startDesktopStream(@RequestBody StreamRequest request) {
//        String command = String.format("ffmpeg -f gdigrab -i desktop -c:v libx264 -pix_fmt yuv420p -f flv %s", request.getRtmpUrl());
//        return startStream(command, request.getRoomId());
//    }
//
//    @PostMapping("/stop-stream")
//    public Result stopStream(@RequestBody StreamRequest request) {
//        Process process = processMap.get(request.getRoomId());
//        if (process != null) {
//            process.destroy();
//            processMap.remove(request.getRoomId());
//            return Result.success();
//        } else {
//            return Result.error("No stream found for the given roomId");
//        }
//    }
//
//    @PostMapping("/record")
//    public Result startRecord(@RequestBody StreamRequest request) {
//        System.out.println("RTMP URL: " + request.getRtmpUrl());
//        System.out.println("Local File Path: " + request.getLocalFilePath());
//        String rtmpUrl = request.getRtmpUrl();
//        String command = String.format("ffmpeg -y -i %s -vcodec copy -t 500  -f mp4 %s ", rtmpUrl,request.getLocalFilePath());
//
//        try {
//
//            Process process = Runtime.getRuntime().exec(command);
//
//            new Thread(() -> {
//                try {
//                    process.waitFor();
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            }).start();
//
//            return Result.success();
//        } catch (IOException e) {
//            e.printStackTrace();
//
//            return Result.error("Failed to start desktop record");
//        }
//    }
//
//    private Result startStream(String command, String roomId) {
//        try {
//            Process process = Runtime.getRuntime().exec(command);
//            processMap.put(roomId, process);
//
//            new Thread(() -> {
//                try {
//                    process.waitFor();
//                    processMap.remove(roomId);
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            }).start();
//
//            return Result.success();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return Result.error("Failed to start stream");
//        }
//    }
//
//
//}
package org.example.livevideoservice.controller;

import org.example.livevideoservice.entity.Result;
import org.example.livevideoservice.entity.StreamRequest;
import org.example.livevideoservice.entity.RoomInfo;
import org.example.livevideoservice.repository.RoomInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class StreamingController {
    private final Map<String, Process> processMap = new ConcurrentHashMap<>();

    @Autowired
    private RoomInfoRepository roomInfoRepository;

    @GetMapping("/camera-devices")
    public Result getCameraDevices() {
        List<String> devices = new ArrayList<>();
        try {
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
            if (exitCode == 0) {
                return Result.success(devices);
            } else {
                return Result.error("exitCode error!!!");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Result.error("IO error!!!");
        }
    }

    @PostMapping("/camera-live")
    public Result startCameraStream(@RequestBody StreamRequest request) {
        String command = String.format("ffmpeg -f dshow -rtbufsize 100M -i video=\"%s\" -vcodec libx264 -preset ultrafast -maxrate 2000k -bufsize 4000k -f flv %s", request.getCameraDevice(), request.getRtmpUrl());
        return startStream(command, request.getRoomId());
    }

    @PostMapping("/desktop-live")
    public Result startDesktopStream(@RequestBody StreamRequest request) {
        String command = String.format("ffmpeg -f gdigrab -i desktop -c:v libx264 -pix_fmt yuv420p -f flv %s", request.getRtmpUrl());
        return startStream(command, request.getRoomId());
    }

    @PostMapping("/stop-stream")
    public Result stopStream(@RequestBody StreamRequest request) {
        Process process = processMap.get(request.getRoomId());
        if (process != null) {
            process.destroy();
            processMap.remove(request.getRoomId());

            // Update room status to 0
            RoomInfo roomInfo = roomInfoRepository.findByRoomID(Integer.parseInt(request.getRoomId()));
            if (roomInfo != null) {
                roomInfo.setStatus(false);
                roomInfoRepository.save(roomInfo);
            }
            return Result.success();
        } else {
            return Result.error("No stream found for the given roomId");
        }
    }

    @PostMapping("/record")
    public Result startRecord(@RequestBody StreamRequest request) {
        System.out.println("RTMP URL: " + request.getRtmpUrl());
        System.out.println("Local File Path: " + request.getLocalFilePath());
        String rtmpUrl = request.getRtmpUrl();
        String command = String.format("ffmpeg -y -i %s -vcodec copy -t 500 -f mp4 %s", rtmpUrl, request.getLocalFilePath());

        try {
            Process process = Runtime.getRuntime().exec(command);

            new Thread(() -> {
                try {
                    process.waitFor();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();

            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("Failed to start desktop record");
        }
    }

    private Result startStream(String command, String roomId) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            processMap.put(roomId, process);

            // Update room status to 1
            RoomInfo roomInfo = roomInfoRepository.findByRoomID(Integer.parseInt(roomId));
            if (roomInfo != null) {
                roomInfo.setStatus(true);
                roomInfoRepository.save(roomInfo);
            }

            new Thread(() -> {
                try {
                    process.waitFor();
                    processMap.remove(roomId);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();

            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("Failed to start stream");
        }
    }
}

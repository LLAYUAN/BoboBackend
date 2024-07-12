package org.example.livevideoservice.controller;

import org.example.livevideoservice.Repository.UserActivityRepository;
import org.example.livevideoservice.entrty.Result;
import org.example.livevideoservice.entrty.StreamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class StreamingController {

    private final Map<String, Process> processMap = new ConcurrentHashMap<>();

    @Autowired
    private UserActivityRepository userActivityRepository;
    @GetMapping("/api/camera-devices")
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
                    // 提取设备名称，假设设备名称被双引号包围
                    int start = line.indexOf('"') + 1;
                    int end = line.lastIndexOf('"');
                    if (start > 0 && end > start) {
                        String deviceName = line.substring(start, end);
                        devices.add(deviceName);
                    }
                }
            }

//            System.out.println("Devices: " + devices);

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

    @PostMapping("/api/camera-live")
    public Result startCameraStream(@RequestBody StreamRequest request) {
        // 根据选择的摄像头设备名称构建 ffmpeg 命令
        System.out.println("Camera Device: " + request.getCameraDevice());
        System.out.println("RTMP URL: " + request.getRtmpUrl());
        String command = String.format("ffmpeg -f dshow -rtbufsize 100M -i video=\"%s\" -vcodec libx264 -preset ultrafast -maxrate 2000k -bufsize 4000k -f flv %s", request.getCameraDevice(), request.getRtmpUrl());

//        if (Objects.equals(request.getLocalFilePath(), "NO PATH")) {
//            command = String.format("ffmpeg -f dshow -rtbufsize 100M -i video=\"%s\" -vcodec libx264 -preset ultrafast -maxrate 2000k -bufsize 4000k -f flv %s", request.getCameraDevice(), request.getRtmpUrl());
//        }else{
//            command = String.format("ffmpeg -f dshow -rtbufsize 100M -i video=\"%s\" -vcodec libx264 -preset ultrafast -maxrate 2000k -bufsize 4000k -f flv %s  -c:v libx264 -preset fast -crf 22 -y %s", request.getCameraDevice(), request.getRtmpUrl(),request.getLocalFilePath());
//        }

        System.out.println("Command: " + command);
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
            return Result.error("Failed to start camera stream");
        }
    }

    @PostMapping("/api/desktop-live")
    public Result startDesktopStream(@RequestBody StreamRequest request) {
        System.out.println("RTMP URL: " + request.getRtmpUrl());
        System.out.println("Local File Path: " + request.getLocalFilePath());
        String rtmpUrl = request.getRtmpUrl();
        String command = String.format("ffmpeg -f gdigrab -i desktop -c:v libx264 -pix_fmt yuv420p -f flv %s", rtmpUrl);
//
//        if (Objects.equals(request.getLocalFilePath(), "NO PATH")) {
//
//            command = String.format("ffmpeg -f gdigrab -i desktop -c:v libx264 -pix_fmt yuv420p -f flv %s", rtmpUrl);
//        }else{
//
//            command = String.format("ffmpeg -f gdigrab -i desktop -c:v libx264 -pix_fmt yuv420p -f flv %s -c:v libx264 -preset fast -crf 22 -y %s", rtmpUrl,request.getLocalFilePath());
//            System.out.println(command);
//        }
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

            return Result.error("Failed to start desktop stream");
        }
    }

    @PostMapping("/api/record")
    public Result startRecord(@RequestBody StreamRequest request) {
        System.out.println("RTMP URL: " + request.getRtmpUrl());
        System.out.println("Local File Path: " + request.getLocalFilePath());
        String rtmpUrl = request.getRtmpUrl();
        String command = String.format("ffmpeg -y -i %s -vcodec copy -t 500  -f mp4 %s ", rtmpUrl,request.getLocalFilePath());

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

    @PostMapping("/api/stop-stream")
    public Result stopStream(@RequestBody StreamRequest request) {
        try {
            // 查找正在进行的ffmpeg进程并杀死它
            String command = "taskkill /F /IM ffmpeg.exe";
            Process process = Runtime.getRuntime().exec(command);

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return Result.success("Stream stopped successfully");
            } else {
                return Result.error("Failed to stop the stream");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Result.error("Error occurred while stopping the stream");
        }
    }

}

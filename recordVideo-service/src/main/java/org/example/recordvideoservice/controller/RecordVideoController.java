package org.example.recordvideoservice.controller;

import org.example.recordvideoservice.dto.UsersAllRecordVideo;
import org.example.recordvideoservice.dto.VideoPagePlayingRecordVideo;
import org.example.recordvideoservice.entity.RecordVideo;
import org.example.recordvideoservice.service.RecordVideoService;
import org.example.recordvideoservice.utils.RecordVideoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class RecordVideoController {
    @Autowired
    private RecordVideoService recordVideoService;
    @Autowired
    private RecordVideoUtils recordVideoUtils;

    @GetMapping("/getUsersRecordVideos")
    public List<UsersAllRecordVideo> getUsersRecordVideos(@RequestParam(name = "userID") String string_userID,@RequestHeader("Authorization") String authorizationHeader) {
        Integer auth_userID = Integer.parseInt(authorizationHeader);
        Integer param_userID = Integer.parseInt(string_userID);
        List<RecordVideo> recordVideoList;
        System.out.println("param_userID:");
        System.out.println(param_userID);
        System.out.println("auth_userID:");
        System.out.println(auth_userID);
        if (param_userID != 0) {
            param_userID = Integer.parseInt(string_userID);
            recordVideoList = recordVideoService.findByUserid(param_userID);
        } else {
            recordVideoList = recordVideoService.findByUserid(auth_userID);
        }
        List<UsersAllRecordVideo> result = new ArrayList<>();
        for (RecordVideo recordVideo : recordVideoList) {
            UsersAllRecordVideo tmp = new UsersAllRecordVideo();
            tmp.setVideoID(recordVideo.getRecordVideoID());
            tmp.setVideoName(recordVideo.getRecordVideoName());
            tmp.setVideoIntro(recordVideo.getRecordVideoIntro());
            tmp.setOwnerName(recordVideo.getUserInfo().getNickname());
            tmp.setImageUrl(recordVideo.getRecordVideoCoverUrl());
            tmp.setUploadTime(recordVideo.getRecordVideoUploadTime());
            result.add(tmp);
        }
        System.out.println("getUsersRecordVideos");
        System.out.println(result);
        return result;
    }

    @GetMapping("/test")
    public String test() {
        return "recordVideoService test success!";
    }

    @GetMapping("/getPlayingRecordVideo")
    public VideoPagePlayingRecordVideo getPlayingRecordVideo(@RequestParam(name = "videoID") String string_videoID) {
        Integer videoID = Integer.parseInt(string_videoID);
        RecordVideo playingRecordVideo = recordVideoService.findRecordVideoByRecordVideoID(videoID);
        VideoPagePlayingRecordVideo result = new VideoPagePlayingRecordVideo();
        result.setVideoName(playingRecordVideo.getRecordVideoName());
        result.setVideoIntro(playingRecordVideo.getRecordVideoIntro());
        result.setVideoUrl(playingRecordVideo.getRecordVideoAddress());
        result.setOwnerUserID(playingRecordVideo.getUserInfo().getUserID());
        result.setOwnerName(playingRecordVideo.getUserInfo().getNickname());
        result.setOwnerIntro(playingRecordVideo.getUserInfo().getSelfIntro());
        result.setUploadTime(playingRecordVideo.getRecordVideoUploadTime());
        result.setOwnerAvatarUrl(playingRecordVideo.getUserInfo().getAvatarUrl());
        return result;
    }



    @PostMapping("/uploadFile")
    //@RequestParam("file") MultipartFile file
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("START uploadFile1111111111111111111111111111");
        if (file.isEmpty()) {
            return "uploadFail";
        }

        try {
            // 获取上传文件的原始名称
            String originalFilename = file.getOriginalFilename();
            // 获取文件的后缀名
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 重新生成唯一文件名
            String newFilename = UUID.randomUUID().toString() + fileExtension;
            // 构建静态资源目录的Path对象
            Path staticFolderPath = Paths.get("static");
            // 确保静态资源目录存在
            Files.createDirectories(staticFolderPath);
            // 构建文件的完整路径
            Path filePath = staticFolderPath.resolve(newFilename);
            // 将文件数据写入到服务器的文件系统中
            System.out.println("filePath");
            System.out.println(filePath);
            file.transferTo(filePath);
            // 返回文件的访问路径
            return "https://123.60.73.77:9999/recordvideo/resources/" + newFilename;
        } catch (IOException e) {
            e.printStackTrace();
            return "uploadFail";
        }
    }

    @GetMapping("/deleteFile")
    public Boolean deleteFile(@RequestParam("fileName") String fileName) {
        try {
            Path path = Paths.get("static/" + fileName);
            if (Files.exists(path)) {
                System.out.println("要删除的文件存在");
                Files.delete(path);
                return true;
            } else {
                System.out.println("要删除的文件不存在");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/saveRecordVideo")
    public Boolean saveRecordVideo(@RequestBody Map<String, Object> recordVideoData, @RequestHeader("Authorization") String authorizationHeader) {
        Integer userID = Integer.parseInt(authorizationHeader);
        RecordVideo recordVideoToSave = recordVideoUtils.transRecordVideoFromFrontend(recordVideoData, userID);
        recordVideoService.saveRecordVideo(recordVideoToSave);
        return true;
    }

    @GetMapping("/deleteRecordVideoByRecordVideoID")
    public Boolean deleteRecordVideoByRecordVideoID(@RequestParam("recordVideoID") String recordVideoID_String) {
        System.out.println("执行删除RecordVideo数据");
        Integer recordVideoIDtoDelete = Integer.parseInt(recordVideoID_String);
        recordVideoService.deleteByRecordVideoID(recordVideoIDtoDelete);
        return true;
    }
}

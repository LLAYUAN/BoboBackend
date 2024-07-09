package org.example.recordvideoservice.controller;

import org.example.recordvideoservice.dto.UsersAllRecordVideo;
import org.example.recordvideoservice.dto.VideoPagePlayingRecordVideo;
import org.example.recordvideoservice.entity.RecordVideo;
import org.example.recordvideoservice.service.RecordVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class RecordVideoController {
    @Autowired
    private RecordVideoService recordVideoService;

    @GetMapping("/getUsersRecordVideos")
    public List<UsersAllRecordVideo> getUsersRecordVideos(@RequestParam(name = "userID") String string_userID,@RequestHeader("Authorization") String authorizationHeader) {
        Integer auth_userID = Integer.parseInt(authorizationHeader);
        Integer param_userID;
        List<RecordVideo> recordVideoList;
        if (!string_userID.isEmpty()) {
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
            result.add(tmp);
        }
        return result;
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
        return result;
    }


    private static String UPLOADED_FOLDER = "/static/";
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("START uploadFile");
        if (file.isEmpty()) {
            return "上传失败";
        }

        try {
            // 获取上传文件的原始名称
            String originalFilename = file.getOriginalFilename();
            // 获取文件的后缀名
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 重新生成唯一文件名
            String newFilename = UUID.randomUUID().toString() + fileExtension;

            // 保存文件到服务器指定目录
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + newFilename);
            Files.write(path, bytes);

            // 返回文件的访问路径
            return "http://localhost:9999/recordvideo/resources/" + newFilename;
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
    }
}

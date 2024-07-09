package org.example.recordvideoservice.service;

import org.example.recordvideoservice.entity.RecordVideo;

import java.util.List;

public interface RecordVideoService {
    List<RecordVideo> findByUserid(Integer userID);
    RecordVideo findRecordVideoByRecordVideoID(Integer recordVideoID);
}

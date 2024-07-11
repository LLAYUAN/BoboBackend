package org.example.recordvideoservice.dao;

import org.example.recordvideoservice.entity.RecordVideo;
import org.example.recordvideoservice.repository.RecordVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordVideoDao {
    List<RecordVideo> findByUserid(Integer userID);
    RecordVideo findRecordVideoByRecordVideoID(Integer recordVideoID);
    void saveRecordVideo(RecordVideo recordVideo);
}

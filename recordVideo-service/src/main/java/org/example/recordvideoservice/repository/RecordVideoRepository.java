package org.example.recordvideoservice.repository;

import org.example.recordvideoservice.entity.RecordVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RecordVideoRepository extends JpaRepository<RecordVideo, Integer> {
    @Query("SELECT v FROM RecordVideo v WHERE v.userInfo.userID = :userid")
    List<RecordVideo> findByUserid(@Param("userid") Integer userID);

    RecordVideo findRecordVideoByRecordVideoID(Integer recordVideoID);
}

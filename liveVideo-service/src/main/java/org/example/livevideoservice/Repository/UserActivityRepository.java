package org.example.livevideoservice.Repository;

import org.example.livevideoservice.entrty.UserActivity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserActivityRepository extends MongoRepository<UserActivity, String> {
    List<UserActivity> findByRoomIdAndExitTimeIsNull(String roomId);
    // 修改这里
    List<UserActivity> findByUserIdAndExitTimeIsNull(String userId);}

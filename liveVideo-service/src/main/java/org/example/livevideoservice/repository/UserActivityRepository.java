package org.example.livevideoservice.repository;

import org.example.livevideoservice.entity.UserActivity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserActivityRepository extends MongoRepository<UserActivity, String> {
    List<UserActivity> findByRoomId(String roomId);
    void deleteByRoomId(String roomId);
}

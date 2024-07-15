package org.example.livevideoservice.repository;

import org.example.livevideoservice.entity.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomInfoRepository extends JpaRepository<RoomInfo, Integer> {
    RoomInfo findByRoomID(Integer roomID);
}
package org.example.userservice.repository;

import org.example.userservice.entity.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomInfoRepo extends JpaRepository<RoomInfo, Integer> {
    public RoomInfo save(RoomInfo roomInfo);
    public RoomInfo findRoomInfoByRoomID(Integer roomID);
}

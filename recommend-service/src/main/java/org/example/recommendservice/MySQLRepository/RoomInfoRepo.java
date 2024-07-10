package org.example.recommendservice.MySQLRepository;

import org.example.recommendservice.entity.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomInfoRepo extends JpaRepository<RoomInfo, Integer>{
}

package org.example.recommendservice.Service;

import org.example.recommendservice.DTO.AddHotIndex;
import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.Dao.RoomDao;
import org.example.recommendservice.entity.RoomHotIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomInfoService {
    @Autowired
    private RoomDao roomDao;

//    public RoomHotIndex saveRoomHotIndex(RoomHotIndex roomHotIndex) {
//        return roomDao.saveRoomHotIndex(roomHotIndex);
//    }
    public void saveRoomHotIndexList(List<RoomHotIndex> roomHotIndexList) {
        roomDao.saveRoomHotIndexList(roomHotIndexList);
    }

    public void  addRoomHotIndex(AddHotIndex addHotIndex){
        roomDao.addRoomHotIndex(addHotIndex);
    }
    public List<RoomCardInfo> getRank(Integer tag){
        return roomDao.getRank(tag);
    }

    public RoomCardInfo getRoomInfo(Integer id){
        return roomDao.getRoomInfo(id);
    }
}

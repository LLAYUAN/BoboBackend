package org.example.recommendservice.Service;

import org.example.recommendservice.DTO.AddHotIndex;
import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.Dao.RoomDao;
import org.example.recommendservice.entity.RoomHotIndex;
import org.example.recommendservice.entity.RoomInfo;
import org.example.recommendservice.utils.HotIndexCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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

//    public void  addRoomHotIndex(AddHotIndex addHotIndex){
//        roomDao.addRoomHotIndex(addHotIndex);
//    }

    public List<RoomCardInfo> getRank(Integer tag){
        System.out.println("tag: " + tag);

        List<RoomInfo> roomInfos = roomDao.getAllRoomInfo();
        List<RoomCardInfo> roomCardInfos = new java.util.ArrayList<>(roomInfos.stream()
                .filter(RoomInfo::getStatus)
                .map(RoomCardInfo::new)
                .filter(roomCardInfo -> tag < 0 || roomCardInfo.getTags().get(tag))
                .toList());
        System.out.println(roomCardInfos);

        roomCardInfos.forEach(roomCardInfo -> {
            RoomHotIndex roomHotIndex = roomDao.getRoomHotIndex(roomCardInfo.getId());
            if (roomHotIndex != null) {
                roomCardInfo.setHotIndex(HotIndexCalculator.calculateHotIndex(roomHotIndex));
            }
        });
        System.out.println(roomCardInfos);

        roomCardInfos.sort(Comparator.comparing(RoomCardInfo::getHotIndex).reversed());

        return roomCardInfos;
    }

    public RoomCardInfo getRoomInfo(Integer id){
        System.out.println("id: " + id);

        RoomInfo roomInfo = roomDao.getRoomInfo(id);
        RoomCardInfo roomCardInfo = new RoomCardInfo(roomInfo);
        RoomHotIndex roomHotIndex = roomDao.getRoomHotIndex(id);

        System.out.println("roomHotIndex" + roomHotIndex);
        if (roomHotIndex != null) {
            System.out.println("Hot Index: " + HotIndexCalculator.calculateHotIndex(roomHotIndex));
            roomCardInfo.setHotIndex(HotIndexCalculator.calculateHotIndex(roomHotIndex));
        }

        return roomCardInfo;
    }
}

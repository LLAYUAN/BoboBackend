package org.example.recommendservice.Service;

import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.Dao.RoomDao;
import org.example.recommendservice.entity.RoomHotIndex;
import org.example.recommendservice.entity.RoomInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomInfoService {
    @Autowired
    private RoomDao roomDao;

    public void saveRoomHotIndexList(List<RoomHotIndex> roomHotIndexList) {
        roomDao.saveRoomHotIndexList(roomHotIndexList);
    }
    public void createRoomHotIndex(int roomId, List<Boolean> tags) {
        RoomHotIndex roomHotIndex = new RoomHotIndex(roomId, tags);
        roomDao.saveRoomHotIndex(roomHotIndex);
    }
    public RoomCardInfo getRoomInfo(Integer id){
        RoomInfo roomInfo = roomDao.getRoomInfo(id);
        RoomHotIndex roomHotIndex = roomDao.getRoomHotIndex(id);
        return new RoomCardInfo(roomInfo, roomHotIndex);
    }
    public int getRoomCount(){
        return roomDao.getRoomCount();
    }
    public List<RoomCardInfo> getRank(Integer tag, Integer page, Integer size) {
        System.out.println("tag: " + tag);

        // 从数据库获取所有RoomHotIndex，并过滤出符合标签的记录
        List<RoomHotIndex> roomHotIndexList = roomDao.getAllRoomHotIndex()
                .stream()
                .filter(roomHotIndex -> tag < 0 || roomHotIndex.getTags().get(tag))
                .sorted(Comparator.comparing(RoomHotIndex::getHotIndex).reversed())
                .toList();

        roomHotIndexList.forEach(System.out::println);

        roomHotIndexList = roomHotIndexList.subList((page - 1) * size, Math.min(page * size, roomHotIndexList.size()));


        // 返回RoomCardInfo列表
        return roomHotIndexList.stream()
                .map(roomHotIndex -> {
                    RoomInfo roomInfo = roomDao.findById(roomHotIndex.getRoomId());
                    return new RoomCardInfo(roomInfo, roomHotIndex);
                })
                .collect(Collectors.toList());
    }
}

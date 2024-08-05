package org.example.recommendservice.Service;

import com.alibaba.fastjson.JSONObject;
import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.Dao.BrowsHistoryDao;
import org.example.recommendservice.Dao.RoomDao;
import org.example.recommendservice.entity.*;
import org.example.recommendservice.utils.LikeIndexCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserBrowsHistoryService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    BrowsHistoryDao browsHistoryDao;
    @Autowired
    RoomDao roomDao;

    private static final int MAX_HISTORY_SIZE = 30; // 最多保存近30条

    public void addBrowsingRecord(String userId, BrowsingRecord browsingRecord) {
        UserBrowsHistory userHistory = browsHistoryDao.getUserBrowsHistory(userId);

        if (userHistory == null) {
            System.out.println("not find by userId");
            userHistory = new UserBrowsHistory(userId, new ArrayList<>());
        }

        List<BrowsingRecord> browsingHistory = userHistory.getBrowsingHistory();
        browsingHistory.add(browsingRecord);

        browsingHistory.sort(Comparator.comparing(BrowsingRecord::getStartTime).reversed());
        if (browsingHistory.size() > MAX_HISTORY_SIZE) {
            browsingHistory = browsingHistory.subList(0, MAX_HISTORY_SIZE);
        }

        userHistory.setBrowsingHistory(browsingHistory);
        browsHistoryDao.saveUserBrowsHistory(userHistory);

        System.out.println("Finished!  userId:  " + userId);
        System.out.println("new browsingRecord:");
        System.out.println(browsingRecord);
        System.out.println("new BrowsingHistory:");
        browsingHistory.forEach(System.out::println);

//        // 直接存，没有最大限制
//        Query query = Query.query(Criteria.where("_id").is(userId));
//        Update update = new Update().push("browsingHistory", browsingRecord);
//        UpdateResult result = mongoTemplate.upsert(query, update, UserBrowsHistory.class);
    }
    public void setBrowsingRecord(String userId, List<BrowsingRecord> browsingHistory) {
        UserBrowsHistory userHistory = browsHistoryDao.getUserBrowsHistory(userId);

        if (userHistory == null) {
            System.out.println("not find by userId");
            userHistory = new UserBrowsHistory(userId, browsingHistory);
        }
        userHistory.setBrowsingHistory(browsingHistory);
        browsHistoryDao.saveUserBrowsHistory(userHistory);

    }

    public JSONObject recommendRooms(String userId) {
        Map<Integer, Integer> rankMap = new HashMap<>();

        List<Integer> popularityBased = PopularityBasedRecommendation();
        System.out.println("popularityBased: "+popularityBased);
        for (Integer integer : popularityBased) {
            rankMap.compute(integer, (k, v) -> (v == null) ? 2 : v + 2);
        }

        List<Integer> randomBased = RandomBasedRecommendation();
        System.out.println("randomBased: "+randomBased);
        for (Integer integer : randomBased) {
            rankMap.compute(integer, (k, v) -> (v == null) ? 2 : v + 2);
        }

        List<Integer> HistoryBased = HistoryBasedRecommendation(userId);
        System.out.println("HistoryBased: "+HistoryBased);
        for (Integer integer : HistoryBased) {
            rankMap.compute(integer, (k, v) -> (v == null) ? 2 : v + 2);
        }

        List<Integer> UserBased = UserBasedCF(userId);
        System.out.println("UserBased: "+UserBased);
        for (int i = 0; i < UserBased.size(); i++) {
            int finalI = i;
            rankMap.compute(UserBased.get(i), (k, v) -> (v == null) ? 5 - finalI : v + 5 - finalI);
        }

        System.out.println("rankMap: " + rankMap);

        List<RoomCardInfo> popularityRoomList = popularityBased
                .stream()
                .map(roomId -> {
                    RoomInfo roomInfo = roomDao.findById(roomId);
                    RoomHotIndex roomHotIndex = roomDao.getRoomHotIndex(roomId);
                    return new RoomCardInfo(roomInfo, roomHotIndex);
                })
                .toList();

        List<RoomCardInfo> recommendRoomList = rankMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(5)
                .map(entry -> {
                    RoomInfo roomInfo = roomDao.findById(entry.getKey());
                    RoomHotIndex roomHotIndex = roomDao.getRoomHotIndex(entry.getKey());
                    return new RoomCardInfo(roomInfo, roomHotIndex);
                })
                .toList();

        JSONObject response = new JSONObject();
        response.put("popularityRoomList", popularityRoomList);
        response.put("recommendRoomList", recommendRoomList);
        return response;
    }
    public List<Integer> PopularityBasedRecommendation() {
        return roomDao.getAllRoomHotIndex()
                .stream()
                .sorted(Comparator.comparing(RoomHotIndex::getHotIndex).reversed())
                .limit(5)
                .map(RoomHotIndex::getRoomId)
                .toList();
    }
    public List<Integer> RandomBasedRecommendation() {
        List<RoomHotIndex> roomHotIndexList = roomDao.getAllRoomHotIndex();
        List<Integer> roomNumbers = new ArrayList<>();

        Random random = new Random();

        int numberOfRoomsToSelect = 3;
        for (int i = 0; i < numberOfRoomsToSelect && i < roomHotIndexList.size(); i++) {
            RoomHotIndex roomInfo = roomHotIndexList.get(random.nextInt(roomHotIndexList.size()));
            roomNumbers.add(roomInfo.getRoomId());
        }

        return roomNumbers;
    }
    public List<Integer> HistoryBasedRecommendation(String userId) {
        UserBrowsHistory userHistory = browsHistoryDao.getUserBrowsHistory(userId);
        if (userHistory == null || userHistory.getBrowsingHistory().isEmpty()) {
            System.out.println("userHistory is null or empty");
            return new ArrayList<>();
        }
        Map<Integer, Long> TagDurationMap = new HashMap<>();

        // 遍历用户浏览历史，统计每个分类的总观看时长
        for (BrowsingRecord record : userHistory.getBrowsingHistory()) {
            RoomInfo roomInfo = roomDao.findById(record.getRoomId());
            List<Boolean> tags = new ArrayList<>();
            tags.add(roomInfo.getStudy());
            tags.add(roomInfo.getEntertain());
            tags.add(roomInfo.getOther());

            long duration = record.getWatchDuration(); // 获取观看时长
            for (int i = 0; i<tags.size();++i){
                if (tags.get(i)){
                    TagDurationMap.merge(i, duration, Long::sum); // 将观看时长累加到对应分类
                }
            }
        }
        int maxTag = TagDurationMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);

        Map<Integer, Integer> roomHotIndexMap = new HashMap<>();

        List<RoomHotIndex> roomHotIndexList = roomDao.getAllRoomHotIndex();
        for (RoomHotIndex roomHotIndex : roomHotIndexList) {
            if(!roomHotIndex.getTags().get(maxTag)) continue;
            roomHotIndexMap.put(roomHotIndex.getRoomId(), roomHotIndex.getHotIndex());
        }

        return roomHotIndexMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
    }
    public List<Integer> UserBasedCF(String userId) {
        // 获取当前用户的浏览记录
        UserBrowsHistory userHistory = browsHistoryDao.getUserBrowsHistory(userId);
        if (userHistory == null || userHistory.getBrowsingHistory().isEmpty()) {
            System.out.println("userHistory is null or empty");
            return new ArrayList<>();
        }

        List<BrowsingRecord> userRecords = userHistory.getBrowsingHistory();
        Set<Integer> watchedRoomIds = userRecords.stream().
                map(BrowsingRecord::getRoomId).collect(Collectors.toSet());
        System.out.println("user "+ userId+ " History: ");
        userRecords.forEach(System.out::println);

        Map<Integer, Integer> userMap = userRecords.stream()
                .collect(Collectors.toMap(
                        BrowsingRecord::getRoomId,
                        LikeIndexCalculator::calculateLikeIndex,
                        Integer::sum
                ));
        System.out.println("UserMap:" + userMap);

        // 计算用户之间的相似度
        Map<String, Double> userSimilarityMap = new HashMap<>();
        List<UserBrowsHistory> allUserHistories = browsHistoryDao.getAllUserBrowsHistory();
        Map<String, Map<Integer, Integer>> compareUserMapList = new HashMap<>();
        for (UserBrowsHistory history : allUserHistories) {
            if (!history.getUserId().equals(userId)) {
                Map<Integer, Integer> compareUserMap = history.getBrowsingHistory().stream()
                        .collect(Collectors.toMap(
                                BrowsingRecord::getRoomId,
                                LikeIndexCalculator::calculateLikeIndex,
                                Integer::sum
                        ));
                compareUserMapList.put(history.getUserId(), compareUserMap);
                userSimilarityMap.put(history.getUserId(), calculateSimilarity(userMap, compareUserMap));
            }
        }
        System.out.println("SimilarityMap:" + userSimilarityMap);

        // 获取相似用户的观看记录
        return userSimilarityMap.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(3) // 选择最相似的前3个用户
                .flatMap(entry -> {
                    Map<Integer, Integer> compareUserMap = compareUserMapList.get(entry.getKey());
                    System.out.println("相似用户id " + entry.getKey() + " UserMap:");
                    System.out.println(compareUserMap);

                    return compareUserMap.entrySet().stream()
                            .filter(entry1 -> !watchedRoomIds.contains(entry1.getKey())) // 过滤掉已经观看过的
                            .filter(entry1 -> roomDao.getRoomHotIndex(entry1.getKey()) != null) // 过滤掉已经下线的
                            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                            .limit(1); // 每个相似用户最多选择1个推荐房间
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
    public double calculateSimilarity(Map<Integer, Integer> userMap, Map<Integer, Integer> compareUserMap) {
        // 实现余弦相似度计算逻辑
        Set<Integer> allRooms = new HashSet<>();
        allRooms.addAll(userMap.keySet());
        allRooms.addAll(compareUserMap.keySet());

        double dotProduct = 0;
        double normA = 0;
        double normB = 0;

        for (Integer roomId : allRooms) {
            double userLikeIndex = userMap.getOrDefault(roomId, 0);
            double compareUserLikeIndex = compareUserMap.getOrDefault(roomId, 0);

            dotProduct += userLikeIndex * compareUserLikeIndex;
            normA += userLikeIndex * userLikeIndex;
            normB += compareUserLikeIndex * compareUserLikeIndex;
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

}

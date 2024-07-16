package org.example.recommendservice.Service;

import org.example.recommendservice.DTO.RoomCardInfo;
import org.example.recommendservice.Dao.BrowsHistoryDao;
import org.example.recommendservice.Dao.RoomDao;
import org.example.recommendservice.entity.*;
import org.example.recommendservice.utils.HotIndexCalculator;
import org.example.recommendservice.utils.SimilarIndexCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

    public List<RoomCardInfo> recommendRooms(String userId) {
        List<Integer> popularityBased = PopularityBasedRecommendation();
        List<Integer> UserBased = UserBasedCF(userId);
        List<Integer> HistoryBased = HistoryBasedRecommendation(userId);
        Map<Integer, Integer> rankMap = new HashMap<>();

        System.out.println("popularityBased: "+popularityBased);
        for (Integer integer : popularityBased) {
            rankMap.compute(integer, (k, v) -> (v == null) ? 2 : v + 2);
        }

        System.out.println("UserBased: "+UserBased);
        for (int i = 0; i < UserBased.size(); i++) {
            int finalI = i;
            rankMap.compute(UserBased.get(i), (k, v) -> (v == null) ? 5 - finalI : v + 5 - finalI);
        }

        System.out.println("HistoryBased: "+HistoryBased);
        for (Integer integer : HistoryBased) {
            rankMap.compute(integer, (k, v) -> (v == null) ? 2 : v + 2);
        }

        System.out.println(rankMap);

        List<Integer> recommendedRoomsID = rankMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
        List<RoomCardInfo> roomCardInfos = recommendedRoomsID.stream()
                .map(roomDao::findById)
                .map(RoomCardInfo::new)
                .toList();
        roomCardInfos.forEach(roomCardInfo -> {
            RoomHotIndex roomHotIndex = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(roomCardInfo.getId())), RoomHotIndex.class);
            if (roomHotIndex != null) {
                roomCardInfo.setHotIndex(HotIndexCalculator.calculateHotIndex(roomHotIndex));
            }
        });
        return roomCardInfos;
    }
    public List<Integer> PopularityBasedRecommendation() {
        List<RoomInfo> roomInfos = roomDao.findAll();

        Map<Integer, Integer> roomHotIndexMap = new HashMap<>();
        for (RoomInfo roomInfo : roomInfos) {
            if (!roomInfo.getStatus()) continue;
            RoomHotIndex roomHotIndex = mongoTemplate.findOne(
                    Query.query(Criteria.where("_id").is(roomInfo.getRoomID())), RoomHotIndex.class);
            if (roomHotIndex != null) {
                int hotIndex = HotIndexCalculator.calculateHotIndex(roomHotIndex);
                roomHotIndexMap.put(roomInfo.getRoomID(), hotIndex);
            }
        }

        return roomHotIndexMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();
    }
    public List<Integer> HistoryBasedRecommendation(String userId) {
        UserBrowsHistory userHistory = browsHistoryDao.getUserBrowsHistory(userId);
        if (userHistory == null || userHistory.getBrowsingHistory().isEmpty()) {
            System.out.println("userHistory is null or empty");
            return new ArrayList<>();
        }
        Map<Integer, Long> categoryDurationMap = new HashMap<>();

        // 遍历用户浏览历史，统计每个分类的总观看时长
        for (BrowsingRecord record : userHistory.getBrowsingHistory()) {
            List<Boolean> category = getCategoryFromRecord(record); // 获取观看记录的分类
            long duration = record.getWatchDuration(); // 获取观看时长
            for (int i = 0; i<category.size();++i){
                if (category.get(i)){
                    categoryDurationMap.merge(i, duration, Long::sum); // 将观看时长累加到对应分类
                }
            }
        }
        int maxTag = categoryDurationMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);


        List<RoomInfo> roomInfos = roomDao.findAll();
        Map<Integer, Integer> roomHotIndexMap = new HashMap<>();
        for (RoomInfo roomInfo : roomInfos) {
            if (!roomInfo.getStatus()) continue;
            if (maxTag == 0 && !roomInfo.getStudy()) continue;
            if (maxTag == 1 && !roomInfo.getEntertain()) continue;
            if (maxTag == 2 && !roomInfo.getOther()) continue;
            RoomHotIndex roomHotIndex = mongoTemplate.findOne(
                    Query.query(Criteria.where("_id").is(roomInfo.getRoomID())), RoomHotIndex.class);
            if (roomHotIndex != null) {
                int hotIndex = HotIndexCalculator.calculateHotIndex(roomHotIndex);
                roomHotIndexMap.put(roomInfo.getRoomID(), hotIndex);
            }
        }

        return roomHotIndexMap.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
    }
    private List<Boolean> getCategoryFromRecord(BrowsingRecord record) {
        RoomInfo roomInfo = roomDao.findById(record.getRoomId());
        List<Boolean> category = new ArrayList<>();
        category.add(roomInfo.getStudy());
        category.add(roomInfo.getEntertain());
        category.add(roomInfo.getOther());
        return category;
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
        System.out.println("userHistory: ");
        userRecords.forEach(System.out::println);

        Map<Integer, Integer> userMap = userRecords.stream()
                .collect(Collectors.toMap(
                        BrowsingRecord::getRoomId,
                        SimilarIndexCalculator::calculateSimilarIndex,
                        Integer::sum
                ));
        System.out.println("UserMap:");
        System.out.println(userMap);

        // 获取所有用户的浏览记录
        List<UserBrowsHistory> allUserHistories = browsHistoryDao.getAllUserBrowsHistory();

        // 计算用户之间的相似度
        Map<String, Double> userSimilarityMap = new HashMap<>();
        for (UserBrowsHistory history : allUserHistories) {
            if (!history.getUserId().equals(userId)) {
                double similarity = calculateSimilarity(userMap, history.getBrowsingHistory());
                userSimilarityMap.put(history.getUserId(), similarity);
            }
        }
        System.out.println("SimilarityMap:");
        System.out.println(userSimilarityMap);

        // 获取相似用户的观看记录
        List<Integer> recommendedRooms = new ArrayList<>();
        userSimilarityMap.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(3) // 选择最相似的前3个用户
                .forEach(entry -> {
                    UserBrowsHistory similarUserHistory = browsHistoryDao.getUserBrowsHistory(entry.getKey());

                    Map<Integer, Integer> tempUserMap = similarUserHistory.getBrowsingHistory().stream()
                            .collect(Collectors.toMap(
                                    BrowsingRecord::getRoomId,
                                    SimilarIndexCalculator::calculateSimilarIndex,
                                    Integer::sum
                            ));
                    System.out.println("相似用户id " + entry.getKey() + " UserMap:");
                    System.out.println(tempUserMap);

                    similarUserHistory.getBrowsingHistory().stream()
                            .filter(record -> !watchedRoomIds.contains(record.getRoomId()))
                            .filter(record -> {
                                RoomInfo roomInfo = roomDao.findById(record.getRoomId());
                                return roomInfo != null && roomInfo.getStatus();
                            })
                            .max(Comparator.comparing(SimilarIndexCalculator::calculateSimilarIndex))
                            .ifPresent(topRecord -> recommendedRooms.add(topRecord.getRoomId()));
                });

        return recommendedRooms;
    }
    private double calculateSimilarity(Map<Integer, Integer> userMap,
                                       List<BrowsingRecord> compareUserRecords) {
        // 实现余弦相似度计算逻辑
        Map<Integer, Integer> compareUserMap = compareUserRecords.stream()
                .collect(Collectors.toMap(
                        BrowsingRecord::getRoomId,
                        SimilarIndexCalculator::calculateSimilarIndex,
                        Integer::sum
                ));

        Set<Integer> allRooms = new HashSet<>();
        allRooms.addAll(userMap.keySet());
        allRooms.addAll(compareUserMap.keySet());

        double dotProduct = 0;
        double normA = 0;
        double normB = 0;

        for (Integer roomId : allRooms) {
            double userSimilarIndex = userMap.getOrDefault(roomId, 0);
            double compareUserSimilarIndex = compareUserMap.getOrDefault(roomId, 0);

            dotProduct += userSimilarIndex * compareUserSimilarIndex;
            normA += userSimilarIndex * userSimilarIndex;
            normB += compareUserSimilarIndex * compareUserSimilarIndex;
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

}

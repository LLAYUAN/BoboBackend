package org.example.userservice.dao;

import org.example.userservice.entity.FollowerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FollowerDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveFollowerInfo(Integer followeeID, Integer followerID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("followeeID").is(followeeID).and("followerID").is(followerID));

        if (mongoTemplate.exists(query, FollowerInfo.class)) {
            // 如果已存在，可以选择抛出异常或者进行其他处理
            throw new RuntimeException("Duplicate followeeId and followerId combination found");
        }

        // 如果不存在重复记录，则保存新的 FollowerInfo 实体
        FollowerInfo followerInfo = new FollowerInfo();
        followerInfo.setFollowerID(followerID);
        followerInfo.setFolloweeID(followeeID);
        followerInfo.setFollowTime(LocalDateTime.now());
        mongoTemplate.save(followerInfo);
    }

    public void deleteFollowerInfo(Integer followeeID, Integer followerID) {
        Query query = new Query();
        //加入查询条件，需要同时满足 followeeID 和 followerID
        query.addCriteria(Criteria.where("followeeID").is(followeeID).and("followerID").is(followerID));

        if (!mongoTemplate.exists(query, FollowerInfo.class)) {
            // 如果不存在，可以选择抛出异常或者进行其他处理
            throw new RuntimeException("No such followeeId and followerId combination found");
        }

        // 如果存在记录，则删除 FollowerInfo 实体
        mongoTemplate.remove(query, FollowerInfo.class);
    }

    public List<Integer> findFolloweesByFollowerID(Integer followerID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("followerID").is(followerID));
        List<FollowerInfo> followerInfos = mongoTemplate.find(query, FollowerInfo.class);
        // 返回 followeeID 列表，即全数字
        return followerInfos.stream()
                .map(FollowerInfo::getFolloweeID)
                .collect(Collectors.toList());
    }

    public List<Integer> findFollowersByFolloweeID(Integer followeeID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("followeeID").is(followeeID));
        List<FollowerInfo> followerInfos = mongoTemplate.find(query, FollowerInfo.class);
        // 返回 followerID 列表，即全数字
        return followerInfos.stream()
                .map(FollowerInfo::getFollowerID)
                .collect(Collectors.toList());
    }

    public boolean checkIsFan(Integer followeeID, Integer followerID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("followeeID").is(followeeID).and("followerID").is(followerID));
        return mongoTemplate.exists(query, FollowerInfo.class);
    }

    public Integer getFollowerCount(Integer userID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("followeeID").is(userID));
        return (int) mongoTemplate.count(query, FollowerInfo.class);
    }

    public Integer getFolloweeCount(Integer userID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("followerID").is(userID));
        return (int) mongoTemplate.count(query, FollowerInfo.class);
    }
}
//
//package org.example.userservice.dao;
//
//import org.example.userservice.entity.FollowerInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//
//@Repository
//public class FollowerDao {
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    private static final String BASE_COLLECTION_NAME = "follower_info";
//
//    public class TableNameUtil {
//        private static final String TABLE_PREFIX = "follower_info_";
//        private static final int MAX_RECORDS_PER_TABLE = 5000;
//
//        public static String getTableName(int index) {
//            return TABLE_PREFIX + index;
//        }
//
//        public static int getTableIndex(long recordCount) {
//            return (int) (recordCount / MAX_RECORDS_PER_TABLE);
//        }
//    }
//
//
//    private String getCurrentCollectionName() {
//        long recordCount = mongoTemplate.count(new Query(), FollowerInfo.class, BASE_COLLECTION_NAME);
//        int tableIndex = TableNameUtil.getTableIndex(recordCount);
//        return TableNameUtil.getTableName(tableIndex);
//    }
//
//    private String getCollectionNameByIndex(int index) {
//        return TableNameUtil.getTableName(index);
//    }
//
//    public void saveFollowerInfo(Integer followeeID, Integer followerID) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("followeeID").is(followeeID).and("followerID").is(followerID));
//
//        String collectionName = getCurrentCollectionName();
//
//        if (mongoTemplate.exists(query, FollowerInfo.class, collectionName)) {
//            throw new RuntimeException("Duplicate followeeId and followerId combination found");
//        }
//
//        FollowerInfo followerInfo = new FollowerInfo();
//        followerInfo.setFollowerID(followerID);
//        followerInfo.setFolloweeID(followeeID);
//        followerInfo.setFollowTime(LocalDateTime.now());
//        mongoTemplate.save(followerInfo, collectionName);
//    }
//
//    public void deleteFollowerInfo(Integer followeeID, Integer followerID) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("followeeID").is(followeeID).and("followerID").is(followerID));
//
//        int currentTableIndex = 0;
//        String collectionName;
//        boolean found = false;
//        do {
//            collectionName = getCollectionNameByIndex(currentTableIndex);
//            if (mongoTemplate.exists(query, FollowerInfo.class, collectionName)) {
//                found = true;
//                break;
//            }
//            currentTableIndex++;
//        } while (mongoTemplate.collectionExists(collectionName));
//
//        if (!found) {
//            throw new RuntimeException("No such followeeId and followerId combination found");
//        }
//
//        mongoTemplate.remove(query, FollowerInfo.class, collectionName);
//    }
//
//    public List<Integer> findFolloweesByFollowerID(Integer followerID) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("followerID").is(followerID));
//
//        List<Integer> followees = new ArrayList<>();
//        int currentTableIndex = 0;
//        String collectionName;
//
//        do {
//            collectionName = getCollectionNameByIndex(currentTableIndex);
//            List<FollowerInfo> followerInfos = mongoTemplate.find(query, FollowerInfo.class, collectionName);
//            followees.addAll(followerInfos.stream().map(FollowerInfo::getFolloweeID).collect(Collectors.toList()));
//            currentTableIndex++;
//        } while (mongoTemplate.collectionExists(collectionName));
//
//        return followees;
//    }
//
//    public List<Integer> findFollowersByFolloweeID(Integer followeeID) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("followeeID").is(followeeID));
//
//        List<Integer> followers = new ArrayList<>();
//        int currentTableIndex = 0;
//        String collectionName;
//
//        do {
//            collectionName = getCollectionNameByIndex(currentTableIndex);
//            List<FollowerInfo> followerInfos = mongoTemplate.find(query, FollowerInfo.class, collectionName);
//            followers.addAll(followerInfos.stream().map(FollowerInfo::getFollowerID).collect(Collectors.toList()));
//            currentTableIndex++;
//        } while (mongoTemplate.collectionExists(collectionName));
//
//        return followers;
//    }
//
//    public boolean checkIsFan(Integer followeeID, Integer followerID) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("followeeID").is(followeeID).and("followerID").is(followerID));
//
//        int currentTableIndex = 0;
//        String collectionName;
//
//        do {
//            collectionName = getCollectionNameByIndex(currentTableIndex);
//            if (mongoTemplate.exists(query, FollowerInfo.class, collectionName)) {
//                return true;
//            }
//            currentTableIndex++;
//        } while (mongoTemplate.collectionExists(collectionName));
//
//        return false;
//    }
//
//    public Integer getFollowerCount(Integer userID) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("followeeID").is(userID));
//
//        int count = 0;
//        int currentTableIndex = 0;
//        String collectionName;
//
//        do {
//            collectionName = getCollectionNameByIndex(currentTableIndex);
//            count += mongoTemplate.count(query, FollowerInfo.class, collectionName);
//            currentTableIndex++;
//        } while (mongoTemplate.collectionExists(collectionName));
//
//        return count;
//    }
//
//    public Integer getFolloweeCount(Integer userID) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("followerID").is(userID));
//
//        int count = 0;
//        int currentTableIndex = 0;
//        String collectionName;
//
//        do {
//            collectionName = getCollectionNameByIndex(currentTableIndex);
//            count += mongoTemplate.count(query, FollowerInfo.class, collectionName);
//            currentTableIndex++;
//        } while (mongoTemplate.collectionExists(collectionName));
//
//        return count;
//    }
//}

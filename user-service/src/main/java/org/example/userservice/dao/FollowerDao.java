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
//@Repository
//public class FollowerDao {
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    public void saveFollowerInfo(Integer followeeID, Integer followerID) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("followeeID").is(followeeID).and("followerID").is(followerID));
//
//        if (mongoTemplate.exists(query, FollowerInfo.class)) {
//            // 如果已存在，可以选择抛出异常或者进行其他处理
//            throw new RuntimeException("Duplicate followeeId and followerId combination found");
//        }
//
//        // 如果不存在重复记录，则保存新的 FollowerInfo 实体
//        FollowerInfo followerInfo = new FollowerInfo();
//        followerInfo.setFollowerID(followerID);
//        followerInfo.setFolloweeID(followeeID);
//        followerInfo.setFollowTime(LocalDateTime.now());
//        mongoTemplate.save(followerInfo);
//    }
//
//    public void deleteFollowerInfo(Integer followeeID, Integer followerID) {
//        Query query = new Query();
//        //加入查询条件，需要同时满足 followeeID 和 followerID
//        query.addCriteria(Criteria.where("followeeID").is(followeeID).and("followerID").is(followerID));
//
//        if (!mongoTemplate.exists(query, FollowerInfo.class)) {
//            // 如果不存在，可以选择抛出异常或者进行其他处理
//            throw new RuntimeException("No such followeeId and followerId combination found");
//        }
//
//        // 如果存在记录，则删除 FollowerInfo 实体
//        mongoTemplate.remove(query, FollowerInfo.class);
//    }
//
//    public List<Integer> findFolloweesByFollowerID(Integer followerID) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("followerID").is(followerID));
//        List<FollowerInfo> followerInfos = mongoTemplate.find(query, FollowerInfo.class);
//        // 返回 followeeID 列表，即全数字
//        return followerInfos.stream()
//                .map(FollowerInfo::getFolloweeID)
//                .collect(Collectors.toList());
//    }
//
//    public List<Integer> findFollowersByFolloweeID(Integer followeeID) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("followeeID").is(followeeID));
//        List<FollowerInfo> followerInfos = mongoTemplate.find(query, FollowerInfo.class);
//        // 返回 followerID 列表，即全数字
//        return followerInfos.stream()
//                .map(FollowerInfo::getFollowerID)
//                .collect(Collectors.toList());
//    }
//
//    public boolean checkIsFan(Integer followeeID, Integer followerID) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("followeeID").is(followeeID).and("followerID").is(followerID));
//        return mongoTemplate.exists(query, FollowerInfo.class);
//    }
//
//    public Integer getFollowerCount(Integer userID) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("followeeID").is(userID));
//        return (int) mongoTemplate.count(query, FollowerInfo.class);
//    }
//
//    public Integer getFolloweeCount(Integer userID) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("followerID").is(userID));
//        return (int) mongoTemplate.count(query, FollowerInfo.class);
//    }
//}

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

    private static final String BASE_COLLECTION_NAME = "follower_info";

    public class TableNameUtil {
        private static final String TABLE_PREFIX = "follower_info_";
        private static final int MAX_RECORDS_PER_TABLE = 5000;

        public static String getTableName(int index) {
            return TABLE_PREFIX + index;
        }

        public static int getTableIndex(long recordCount) {
            return (int) (recordCount / MAX_RECORDS_PER_TABLE);
        }
    }

    private String getCurrentCollectionName() {
        long recordCount = mongoTemplate.count(new Query(), FollowerInfo.class, BASE_COLLECTION_NAME);
        int tableIndex = TableNameUtil.getTableIndex(recordCount);
        return TableNameUtil.getTableName(tableIndex);
    }

    private String getCollectionNameByIndex(int index) {
        return TableNameUtil.getTableName(index);
    }

    private List<String> getAllCollectionNames() {
        List<String> collectionNames = new ArrayList<>();
        int currentTableIndex = 0;
        String collectionName;

        do {
            collectionName = getCollectionNameByIndex(currentTableIndex);
            if (mongoTemplate.collectionExists(collectionName)) {
                collectionNames.add(collectionName);
            }
            currentTableIndex++;
        } while (mongoTemplate.collectionExists(collectionName));

        return collectionNames;
    }

    public void saveFollowerInfo(Integer followeeID, Integer followerID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("followeeID").is(followeeID).and("followerID").is(followerID));

        // 并行检查所有表中是否存在该条目
        boolean exists = getAllCollectionNames().parallelStream()
                .anyMatch(collectionName -> mongoTemplate.exists(query, FollowerInfo.class, collectionName));

        if (exists) {
            throw new RuntimeException("Duplicate followeeId and followerId combination found");
        }

        // 保存新的 FollowerInfo 实体到当前表中
        String collectionName = getCurrentCollectionName();
        FollowerInfo followerInfo = new FollowerInfo();
        followerInfo.setFollowerID(followerID);
        followerInfo.setFolloweeID(followeeID);
        followerInfo.setFollowTime(LocalDateTime.now());
        mongoTemplate.save(followerInfo, collectionName);
    }

    public void deleteFollowerInfo(Integer followeeID, Integer followerID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("followeeID").is(followeeID).and("followerID").is(followerID));

        // 并行删除所有满足条件的条目
        List<String> deletedCollections = getAllCollectionNames().parallelStream()
                .filter(collectionName -> mongoTemplate.exists(query, FollowerInfo.class, collectionName))
                .peek(collectionName -> mongoTemplate.remove(query, FollowerInfo.class, collectionName))
                .collect(Collectors.toList());

        if (deletedCollections.isEmpty()) {
            throw new RuntimeException("No such followeeId and followerId combination found");
        }
    }

    public List<Integer> findFolloweesByFollowerID(Integer followerID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("followerID").is(followerID));

        // 并行查询所有表
        return getAllCollectionNames().parallelStream()
                .flatMap(collectionName -> mongoTemplate.find(query, FollowerInfo.class, collectionName).stream())
                .map(FollowerInfo::getFolloweeID)
                .collect(Collectors.toList());
    }

    public List<Integer> findFollowersByFolloweeID(Integer followeeID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("followeeID").is(followeeID));

        // 并行查询所有表
        return getAllCollectionNames().parallelStream()
                .flatMap(collectionName -> mongoTemplate.find(query, FollowerInfo.class, collectionName).stream())
                .map(FollowerInfo::getFollowerID)
                .collect(Collectors.toList());
    }

    public boolean checkIsFan(Integer followeeID, Integer followerID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("followeeID").is(followeeID).and("followerID").is(followerID));

        // 并行检查所有表
        return getAllCollectionNames().parallelStream()
                .anyMatch(collectionName -> mongoTemplate.exists(query, FollowerInfo.class, collectionName));
    }

    public Integer getFollowerCount(Integer userID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("followeeID").is(userID));

        // 并行统计所有表中的记录数
        return getAllCollectionNames().parallelStream()
                .mapToInt(collectionName -> (int) mongoTemplate.count(query, FollowerInfo.class, collectionName))
                .sum();
    }

    public Integer getFolloweeCount(Integer userID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("followerID").is(userID));

        // 并行统计所有表中的记录数
        return getAllCollectionNames().parallelStream()
                .mapToInt(collectionName -> (int) mongoTemplate.count(query, FollowerInfo.class, collectionName))
                .sum();
    }
}
package org.example.recommendservice.Service;

import org.example.recommendservice.Dao.BrowsHistoryDao;
import org.example.recommendservice.entity.BrowsingRecord;
import org.example.recommendservice.entity.UserBrowsHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class UserBrowsHistoryService {
//    偷懒用下面的了
//    private final MongoTemplate mongoTemplate;
//    @Autowired
//    public UserBrowsHistoryService(MongoTemplate mongoTemplate) {
//        this.mongoTemplate = mongoTemplate;
//    }

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    BrowsHistoryDao browsHistoryDao;

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
        System.out.println("BrowsingHistory:");
        browsingHistory.forEach(System.out::println);

//        // 直接存，没有最大限制
//        Query query = Query.query(Criteria.where("_id").is(userId));
//        Update update = new Update().push("browsingHistory", browsingRecord);
//        UpdateResult result = mongoTemplate.upsert(query, update, UserBrowsHistory.class);
    }
}

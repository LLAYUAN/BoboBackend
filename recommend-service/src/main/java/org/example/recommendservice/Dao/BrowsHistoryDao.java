package org.example.recommendservice.Dao;

import org.example.recommendservice.entity.UserBrowsHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BrowsHistoryDao {
    @Autowired
    MongoTemplate mongoTemplate;

    public UserBrowsHistory getUserBrowsHistory(String userId){
        Query query = Query.query(Criteria.where("_id").is(userId));  // 必须按照字段名查而不是变量名
        return mongoTemplate.findOne(query, UserBrowsHistory.class);
    }
    public void saveUserBrowsHistory(UserBrowsHistory userBrowsHistory){
        mongoTemplate.save(userBrowsHistory);
    }
}

package org.example.recommendservice.Dao;

import org.example.recommendservice.entity.UserBrowsHistory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BrowsHistoryDao.class)
public class BrowsHistoryDaoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BrowsHistoryDao browsHistoryDao;

    @MockBean
    private MongoTemplate mockMongoTemplate;

    @Test
    public void testGetUserBrowsHistory() {
        String userId = "testUser";
        UserBrowsHistory expectedHistory = new UserBrowsHistory(userId, new ArrayList<>());

        Query query = Query.query(Criteria.where("_id").is(userId));
        Mockito.when(mockMongoTemplate.findOne(query, UserBrowsHistory.class)).thenReturn(expectedHistory);

        UserBrowsHistory result = browsHistoryDao.getUserBrowsHistory(userId);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
    }

    @Test
    public void testGetAllUserBrowsHistory() {
        List<UserBrowsHistory> expectedHistoryList = new ArrayList<>();
        expectedHistoryList.add(new UserBrowsHistory("user1", new ArrayList<>()));
        expectedHistoryList.add(new UserBrowsHistory("user2", new ArrayList<>()));

        Mockito.when(mockMongoTemplate.findAll(UserBrowsHistory.class)).thenReturn(expectedHistoryList);

        List<UserBrowsHistory> result = browsHistoryDao.getAllUserBrowsHistory();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getUserId()).isEqualTo("user1");
        assertThat(result.get(1).getUserId()).isEqualTo("user2");
    }

    @Test
    public void testSaveUserBrowsHistory() {
        UserBrowsHistory userBrowsHistory = new UserBrowsHistory("testUser", new ArrayList<>());

        browsHistoryDao.saveUserBrowsHistory(userBrowsHistory);

        // Verify that save method was called on mockMongoTemplate
        Mockito.verify(mockMongoTemplate).save(userBrowsHistory);
    }
}

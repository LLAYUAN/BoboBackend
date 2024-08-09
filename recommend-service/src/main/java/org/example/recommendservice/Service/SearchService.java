package org.example.recommendservice.Service;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.example.recommendservice.DTO.RoomCardInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private RoomInfoService roomInfoService;

    private JiebaSegmenter segmenter;

    @Autowired
    private UserBrowsHistoryService userBrowsHistoryService;

    public SearchService() {
        this.segmenter = new JiebaSegmenter();
    }

    public List<RoomCardInfo> searchByRelevancy(String query, String userId) {
        List<RoomCardInfo> rankList = roomInfoService.getRank(-1,1,1000);

        // Levenshtein距离实例
        LevenshteinDistance levenshtein = new LevenshteinDistance();

        // 分词
        List<String> queryTokens = segmentWords(query);

        // 进行模糊搜索并根据相关度排序
        return rankList.stream()
                .filter(room -> isMatch(queryTokens, room, levenshtein))
                .sorted(Comparator.comparingInt(room -> -calculateRelevancy(queryTokens, room, levenshtein)))
                .collect(Collectors.toList());
    }

    private boolean isMatch(List<String> queryTokens, RoomCardInfo room, LevenshteinDistance levenshtein) {
        List<String> roomNameTokens = segmentWords(room.getRoomName());
        List<String> descriptionTokens = segmentWords(room.getDescription());
        List<String> userNameTokens = segmentWords(room.getUserName());

        // 可以根据需要调整匹配阈值，这里设置为最大允许距离为1
        int threshold = 1;

        // 判断 query 与 roomName, description, userName 是否匹配
        return queryTokens.stream().anyMatch(queryToken ->
                roomNameTokens.stream().anyMatch(token -> levenshtein.apply(queryToken, token) <= threshold) ||
                        descriptionTokens.stream().anyMatch(token -> levenshtein.apply(queryToken, token) <= threshold) ||
                        userNameTokens.stream().anyMatch(token -> levenshtein.apply(queryToken, token) <= threshold)
        );
    }

    private int calculateRelevancy(List<String> queryTokens, RoomCardInfo room, LevenshteinDistance levenshtein) {
        List<String> roomNameTokens = segmentWords(room.getRoomName());
        List<String> descriptionTokens = segmentWords(room.getDescription());
        List<String> userNameTokens = segmentWords(room.getUserName());

        // 计算相关度分数，匹配的次数越多，距离越小，相关度越高
        int relevancyScore = 0;
        int threshold = 1;

        for (String queryToken : queryTokens) {
            for (String token : roomNameTokens) {
                int distance = levenshtein.apply(queryToken, token);
                if (distance <= threshold) {
                    relevancyScore += (threshold - distance);
                }
            }
            for (String token : descriptionTokens) {
                int distance = levenshtein.apply(queryToken, token);
                if (distance <= threshold) {
                    relevancyScore += (threshold - distance);
                }
            }
            for (String token : userNameTokens) {
                int distance = levenshtein.apply(queryToken, token);
                if (distance <= threshold) {
                    relevancyScore += (threshold - distance);
                }
            }
        }

        return relevancyScore;
    }

    private List<String> segmentWords(String text) {
        return segmenter.process(text, JiebaSegmenter.SegMode.SEARCH).stream()
                .map(token -> token.word)
                .collect(Collectors.toList());
    }
}

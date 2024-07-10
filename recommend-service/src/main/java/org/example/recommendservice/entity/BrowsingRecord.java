package org.example.recommendservice.entity;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrowsingRecord {
    private Integer roomId;
    private int messageCount; // 发送弹幕数量
    private boolean liked; // 是否点赞
    private int shares; // 分享次数
    private int followStatus; // 关注状态 (-1 取消关注, 0 维持, 1 新增关注)
    private long watchDuration; // 观看时长（单位是秒）
    private LocalDateTime startTime; // 开始观看的时间
}
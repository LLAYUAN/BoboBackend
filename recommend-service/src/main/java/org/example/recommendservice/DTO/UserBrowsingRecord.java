package org.example.recommendservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.recommendservice.entity.BrowsingRecord;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBrowsingRecord {
    private String userId;
    private BrowsingRecord browsingRecord;
}
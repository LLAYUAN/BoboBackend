package org.example.recommendservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("test1")
public class TestEntity {
    @Id
    private Integer id;

    @Field("userId")
    private int userId;

    // 没有指定就默认用变量名称
    @Field
    private int age;
}

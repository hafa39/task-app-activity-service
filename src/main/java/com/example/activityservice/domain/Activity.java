package com.example.activityservice.domain;

import com.example.activityservice.event.ActivityProxy;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("activities")
public record Activity(
        @Id
        Long id,
        String userId,
        Long cardId,
        String type,
        String detail,
        Instant createdDate) {

   public static Activity fromProxy(ActivityProxy proxy){
           return new Activity(null,proxy.userId(),
                   proxy.cardId(),
                   proxy.activityType(),
                   proxy.detail(),
                   Instant.now());
   }
}

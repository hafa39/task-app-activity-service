package com.example.activityservice.event;
public record ActivityProxy(
        String userId,
        long cardId,
        String activityType,
        String detail) {

}




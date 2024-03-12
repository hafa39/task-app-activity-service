package com.example.activityservice.event;

import com.example.activityservice.domain.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ActivityFunctions {

    private static final Logger log =
            LoggerFactory.getLogger(ActivityFunctions.class);

    @Bean
    public Consumer<ActivityProxy> acceptActivity(ActivityService activityService){
        return activityProxy -> activityService.saveFromProxy(activityProxy);
    }

    @Bean
    public Consumer<Long> removeActivities(ActivityService activityService){
        return cardId -> activityService.removeByCard(cardId);
    }
}

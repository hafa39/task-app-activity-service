package com.example.activityservice.domain;

import com.example.activityservice.event.ActivityProxy;

import java.util.List;

public interface ActivityService {

    /**
     * Save an activity
     *
     * @param activity the activity instance
     */
    void saveActivity(Activity activity);

    void saveFromProxy(ActivityProxy activityProxy);

    List<Activity> findCardActivities(Long cardId);

    void removeByCard(Long cardId);

    void removeComment(Long commentId);

    Activity getActivity(long activityId);
}

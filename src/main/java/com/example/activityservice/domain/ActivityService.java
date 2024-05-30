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

    /**
     * Save an activity from a proxy
     *
     * @param activityProxy the activity proxy instance
     */
    void saveFromProxy(ActivityProxy activityProxy);

    /**
     * Find activities for a card
     *
     * @param cardId the card id
     * @return a list of activities
     */
    List<Activity> findCardActivities(Long cardId);

    /**
     * Remove activities for a card
     *
     * @param cardId the card id
     */
    void removeByCard(Long cardId);

    /**
     * Remove a comment
     *
     * @param commentId the activity id
     */
    void removeComment(Long commentId);

    /**
     *
     * @param activityId
     * @return
     */
    Activity getActivity(long activityId);
}

package com.example.activityservice.domain;

import com.example.activityservice.event.ActivityProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService{
    private static final Logger log =
            LoggerFactory.getLogger(ActivityServiceImpl.class);
    private ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public void saveActivity(Activity activity) {
        activityRepository.save(activity);
    }

    @Override
    public void saveFromProxy(ActivityProxy activityProxy) {
        String activityType = activityProxy.activityType();
        if (ActivityType.DELETE_COMMENT.getType().equals(activityType)){
            Long commentId = Long.valueOf(activityProxy.detail());
            activityRepository.deleteById(commentId);
        }
        Activity activity = Activity.fromProxy(activityProxy);
        Activity savedActivity = activityRepository.save(activity);
        log.info("Saved activity: cardId = {}, type = {}, detail = {}",savedActivity.cardId(),savedActivity.type(),savedActivity.detail());
    }

    @Override
    public List<Activity> findCardActivities(Long cardId) {
        List<Activity> result = new ArrayList<>();
        activityRepository.findByCardId(cardId).forEach(result::add);
        return result;
    }

    @Override
    public void removeByCard(Long cardId) {
        activityRepository.deleteByCardId(cardId);
        log.info("All activities with cardId = {} are removed",cardId);
    }

    @Override
    public void removeComment(Long commentId) {
        Activity activity = activityRepository.findById(commentId).orElseThrow();
        if(activity.type().equals("add-comment")){
            activityRepository.delete(activity);
        }
    }

    @Override
    public Activity getActivity(long activityId) {
        return activityRepository.findById(activityId).orElseThrow();
    }
}

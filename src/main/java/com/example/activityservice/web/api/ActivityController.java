package com.example.activityservice.web.api;

import com.example.activityservice.domain.Activity;
import com.example.activityservice.domain.ActivityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    private ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping()
    public List<Activity> getCardActivities(@RequestParam(name = "cardId") Long cardId) {
        return activityService.findCardActivities(cardId);
    }

    @GetMapping("/{activityId}")
    public Activity getActivity(@PathVariable long activityId){
        return activityService.getActivity(activityId);
    }

    /*@DeleteMapping()
    public  void removeCommentActivity(@RequestParam("commentId") Long commentId){
        activityService.removeComment(commentId);
    }*/


}

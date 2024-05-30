package com.example.activityservice.domain;

import com.example.activityservice.event.ActivityProxy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ActivityServiceImplTest {
    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityServiceImpl activityService;

    @Test
    void testSaveFromProxy() {
        Activity a = new Activity(null, "userid", 1l,
                ActivityType.CHANGE_CARD_TITLE.getType(), "detail", Instant.now());
        ActivityProxy proxy = new ActivityProxy(a.userId(), a.cardId(), a.type(), a.detail());

        when(activityRepository.save(any(Activity.class))).thenReturn(a);
        // Call the method under test
        activityService.saveFromProxy(proxy);

        // Verify that the appropriate methods were called
        verify(activityRepository, times(1)).save(any(Activity.class));
        verifyNoMoreInteractions(activityRepository);
    }
}
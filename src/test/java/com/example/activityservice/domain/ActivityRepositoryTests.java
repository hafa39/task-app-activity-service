package com.example.activityservice.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJdbcTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class ActivityRepositoryTests {

    @Autowired
    ActivityRepository activityRepository;

    @Container
    static PostgreSQLContainer<?> database = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.4"));

    static {
        database.start();
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
    }

    @Test
    void findByCardId() {
        Long cardId = 10L;
        Activity activity = new Activity(null, "userId", cardId, "comment", "detail", Instant.now());
        activityRepository.save(activity);

        Iterable<Activity> byCardId = activityRepository.findByCardId(cardId);
        assertThat(byCardId).hasSize(1);
        assertThat(byCardId).allMatch(act -> act.cardId().equals(cardId));
    }

    @Test
    void deleteByCardId() {
        Long cardId = 10L;
        Activity activity = new Activity(null, "userId", cardId, "comment", "detail", Instant.now());
        Activity s1 = activityRepository.save(activity);
        Activity s2= activityRepository.save(activity);

        Iterable<Activity> all = activityRepository.findAll();
        assertThat(all).hasSize(2);

        activityRepository.deleteByCardId(cardId);
        Iterable<Activity> afterRemove = activityRepository.findAll();
        assertThat(afterRemove).hasSize(0);
    }
}
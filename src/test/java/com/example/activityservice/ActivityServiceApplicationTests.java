package com.example.activityservice;

import com.example.activityservice.domain.Activity;
import com.example.activityservice.domain.ActivityRepository;
import com.example.activityservice.domain.ActivityType;
import com.example.activityservice.event.ActivityProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Import(TestChannelBinderConfiguration.class)
class ActivityServiceApplicationTests {

    @Container
    static PostgreSQLContainer<?> database = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.4"));

    static {
        database.start();
    }

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OutputDestination output;

    @Autowired
    private InputDestination input;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        /*registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloakContainer.getAuthServerUrl() + "realms/TaskAgile");*/
        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
    }
    @BeforeEach
    void setUp(){
        activityRepository.deleteAll();
    }
    @Test
    void whenSendActivityThenSave(){
        var proxy =
                new ActivityProxy("userId", 1l, ActivityType.ADD_COMMENT.getType(), "detail");

        Message<ActivityProxy> inputMessage = MessageBuilder
                .withPayload(proxy).build();

        input.send(inputMessage);

        Iterable<Activity> byCardId = activityRepository.findByCardId(1l);
        assertThat(byCardId)
                .hasSize(1)
                .allMatch(activity -> activity.detail().equals(proxy.detail()));
    }
}

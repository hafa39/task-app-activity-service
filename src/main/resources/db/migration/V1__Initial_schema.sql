CREATE TABLE activities
(
    id          SERIAL PRIMARY KEY,
    user_id      VARCHAR(255),
    card_id     BIGINT,
    type        VARCHAR(255),
    detail      VARCHAR(255),
    created_date TIMESTAMP
);



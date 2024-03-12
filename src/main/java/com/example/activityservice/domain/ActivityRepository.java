package com.example.activityservice.domain;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ActivityRepository extends CrudRepository<Activity,Long> {

    Iterable<Activity> findByCardId(Long cardId);

    @Modifying
    @Query("delete from activities a where a.card_id = :cardId")
    void deleteByCardId(@Param("cardId") Long cardId);
}

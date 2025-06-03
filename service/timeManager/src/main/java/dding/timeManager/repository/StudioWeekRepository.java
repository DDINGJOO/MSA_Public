package dding.timeManager.repository;


import dding.timeManager.entity.Studio.StudioWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudioWeekRepository extends JpaRepository<StudioWeek, Long> {

    StudioWeek findByStudioIdAndDayOfWeekAndHour(String studioId, int dayOfWeek, int hour);

    boolean existsByStudioIdAndDayOfWeekAndHour(String studioId, int dayOfWeek, int hour);


    void deleteAllByStudioId(String studioId);


    boolean existsByStudioId(String studioId);


    void deleteAllByStudioIdAndOverriddenIsTrue(String studioId);


    List<StudioWeek> findAllByStudioId(String studioId);


    void deleteByStudioIdAndDayOfWeekAndHourAndOverriddenIsFalse(String studioId, int dayOfWeek, int hour);



}

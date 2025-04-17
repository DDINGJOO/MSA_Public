package dding.timeManager.repository;


import dding.timeManager.entity.Studio.StudioWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudioWeekRepository extends JpaRepository<StudioWeek, Long> {

    StudioWeek findByStudioIdAndDayOfWeekAndHour(String studioId, int dayOfWeek, int hour);

    boolean existsByStudioIdAndDayOfWeekAndHour(String studioId, int dayOfWeek, int hour);

    void deleteByStudioId(String studioId);

    List<StudioWeek> findByStudioIdAndDayOfWeek(String studioId, int dayOfWeek);

    void deleteByStudioIdAndDayOfWeek(String studioId, int dayOfWeek);

}

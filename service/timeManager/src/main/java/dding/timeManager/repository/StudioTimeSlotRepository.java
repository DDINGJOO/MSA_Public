package dding.timeManager.repository;

import dding.timeManager.entity.StudioTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudioTimeSlotRepository  extends JpaRepository<StudioTimeSlot,Long> {
    List<StudioTimeSlot> findByStudioId(String studioId);
    void deleteAllByStudioId(String studioId);

    void deleteByStudioId(String studioId);
}


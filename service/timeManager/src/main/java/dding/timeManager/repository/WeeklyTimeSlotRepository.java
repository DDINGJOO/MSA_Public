package dding.timeManager.repository;


import dding.timeManager.entity.WeeklyTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeeklyTimeSlotRepository extends JpaRepository<WeeklyTimeSlot, String> {

    List<WeeklyTimeSlot> findByObjectId(String objectId);

    void deleteByObjectId(String objectId);



}



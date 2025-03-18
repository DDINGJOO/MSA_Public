package dding.timeManager.repository;


import dding.timeManager.entity.CommonTimeSlots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonTimeSlotsRepository extends JpaRepository<CommonTimeSlots, String> {
     List<CommonTimeSlots> findAllByObjectId(String objectId);
}

package dding.timeManager.repository;


import dding.timeManager.entity.UserTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTimeSlotRepository extends JpaRepository<UserTimeSlot, Long> {

    List<UserTimeSlot> findByUserId(String userId);

    void deleteByUserId(String userId);
}


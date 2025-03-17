package dding.timeManager.service;


import dding.timeManager.dto.request.UserTimeSlotRequest;
import dding.timeManager.dto.response.UserTimeSlotResponse;
import dding.timeManager.entity.UserTimeSlot;
import dding.timeManager.repository.UserTimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTimeSlotService {

    private final UserTimeSlotRepository userTimeSlotRepository;

    @Transactional
    public void save(UserTimeSlotRequest request) {
        UserTimeSlot slot = UserTimeSlot.builder()
                .userId(request.getUserId())
                .dayOfWeek(DayOfWeek.valueOf(request.getDayOfWeek()))
                .hour(request.getHour())
                .available(request.isAvailable())
                .build();
        userTimeSlotRepository.save(slot);
    }


    //TODO : IMPL
    public void update(UserTimeSlotRequest request) {

    }

    public List<UserTimeSlotResponse> findByUserId(String userId) {
        List<UserTimeSlot> slots = userTimeSlotRepository.findByUserId(userId);
        return slots.stream()
                .map(slot -> new UserTimeSlotResponse(
                        slot.getDayOfWeek().name(),
                        slot.getHour(),
                        slot.isAvailable()
                )).collect(Collectors.toList());
    }


    public void deleteAllByUserId(String userId) {
        userTimeSlotRepository.deleteByUserId(userId);
    }
}
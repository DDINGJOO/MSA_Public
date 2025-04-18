package dding.timeManager.service.User;

import dding.timeManager.dto.request.User.TimeSlotRequest;
import dding.timeManager.dto.response.User.TimeSlotResponse;
import dding.timeManager.entity.user.UserTimeSlot;
import dding.timeManager.repository.UserTimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTimeSlotService {

    private final UserTimeSlotRepository userTimeSlotRepository;

    public void addTimeSlot(String userId, TimeSlotRequest request) {
        if (userTimeSlotRepository.existsByUserIdAndDayOfWeekAndHour(userId, request.getDayOfWeek(), request.getHour())) {
            throw new IllegalArgumentException("이미 존재하는 시간 슬롯입니다.");
        }
        UserTimeSlot slot = userTimeSlotRepository.save(
                UserTimeSlot.builder()
                        .userId(userId)
                        .dayOfWeek(request.getDayOfWeek())
                        .hour(request.getHour())
                        .available(true)

                        .build()
        );

        userTimeSlotRepository.save(slot);
    }

    public List<TimeSlotResponse> getUserTimeSlots(String userId) {
        return userTimeSlotRepository.findByUserId(userId).stream()
                .map(slot -> TimeSlotResponse.builder()
                        .id(slot.getId())
                        .dayOfWeek(slot.getDayOfWeek())
                        .hour(slot.getHour())
                        .build())
                .collect(Collectors.toList());
    }

    public void deleteUserTimeSlot(Long id) {
        userTimeSlotRepository.deleteById(id);
    }

    public void addTimeSlots(String userId, List<TimeSlotRequest> requests) {
        List<UserTimeSlot> newSlots = new ArrayList<>();

        for (TimeSlotRequest request : requests) {
            boolean exists = userTimeSlotRepository.existsByUserIdAndDayOfWeekAndHour(
                    userId,
                    request.getDayOfWeek(),
                    request.getHour()
            );

            if (!exists) {
                newSlots.add(
                        UserTimeSlot.builder()
                                .userId(userId)
                                .dayOfWeek(request.getDayOfWeek())
                                .hour(request.getHour())
                                .available(true)
                                .build()
                );
            }
        }

        userTimeSlotRepository.saveAll(newSlots);
    }

}

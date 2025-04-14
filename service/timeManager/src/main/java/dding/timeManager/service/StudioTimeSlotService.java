package dding.timeManager.service;

import dding.timeManager.dto.request.StudioTimeSlotBatchRequest;
import dding.timeManager.entity.Studio.StudioTimeSlot;
import dding.timeManager.repository.StudioTimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StudioTimeSlotService {
    private final StudioTimeSlotRepository studioTimeSlotRepository;


    @Transactional
    public void saveTimeSlots(StudioTimeSlotBatchRequest request) {
        List<StudioTimeSlot> slots = new ArrayList<>();
        int startHour = 0;
        int endHour = 24;

        //  24시간 체크 false 면  프론트에서 받은 시간대로 설정
        if (!request.isCheck24Hours()) {
            startHour = LocalTime.parse(request.getStartTime()).getHour();
            endHour = LocalTime.parse(request.getEndTime()).getHour();
        }

        for (int hour = startHour; hour < endHour; hour++) {
            StudioTimeSlot slot = StudioTimeSlot.builder()
                    .studioId(request.getStudioId())
                    .dayOfWeek(request.getDayOfWeek())
                    .hour(hour)
                    .available(true)
                    .isHoliday(request.isHoliday())
                    .build();
            slots.add(slot);
        }

        studioTimeSlotRepository.saveAll(slots);
    }
}

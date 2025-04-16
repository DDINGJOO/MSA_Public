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
    public void updateTimeSlots(StudioTimeSlotBatchRequest request)
    {
        studioTimeSlotRepository.deleteAllByStudioId(request.getStudioId());
        saveTimeSlots(request);
    }


    @Transactional
    public void saveTimeSlots(StudioTimeSlotBatchRequest request) {
        List<StudioTimeSlot> slots = new ArrayList<>();

        // 시작 시간은 LocalTime.parse로 변환
        int startHour = LocalTime.parse(request.getStartTime()).getHour();

        // 종료 시간이 "24:00"이면 별도 처리, 그렇지 않으면 파싱
        int endHour;
        if ("24:00".equals(request.getEndTime())) {
            endHour = 24;
        } else {
            endHour = LocalTime.parse(request.getEndTime()).getHour();
        }

        // 만약 종료 시간이 시작 시간보다 작거나 같다면, 자정을 넘어간 것으로 간주
        if (endHour <= startHour) {
            // 1부: 시작 시간부터 23시까지 슬롯 생성 (24시를 포함하려면 24시 대신 반복 조건에서 24까지)
            for (int hour = startHour; hour < 24; hour++) {
                StudioTimeSlot slot = StudioTimeSlot.builder()
                        .studioId(request.getStudioId())
                        .dayOfWeek(request.getDayOfWeek())
                        .hour(hour)
                        .available(true) // TODO: 실제 가용 여부 로직 적용
                        .isHoliday(request.isHoliday())
                        .build();
                slots.add(slot);
            }
            // 2부: 자정(0시)부터 종료 시간까지 슬롯 생성
            for (int hour = 0; hour < endHour; hour++) {
                StudioTimeSlot slot = StudioTimeSlot.builder()
                        .studioId(request.getStudioId())
                        .dayOfWeek(request.getDayOfWeek())
                        .hour(hour)
                        .available(true) // TODO: 실제 가용 여부 로직 적용
                        .isHoliday(request.isHoliday())
                        .build();
                slots.add(slot);
            }
        } else {
            // 일반적인 경우: 시작 시간부터 종료 시간 전까지 슬롯 생성
            for (int hour = startHour; hour < endHour; hour++) {
                StudioTimeSlot slot = StudioTimeSlot.builder()
                        .studioId(request.getStudioId())
                        .dayOfWeek(request.getDayOfWeek())
                        .hour(hour)
                        .available(true) // TODO: 실제 가용 여부 로직 적용
                        .isHoliday(request.isHoliday())
                        .build();
                slots.add(slot);
            }
        }

        studioTimeSlotRepository.saveAll(slots);
    }

}

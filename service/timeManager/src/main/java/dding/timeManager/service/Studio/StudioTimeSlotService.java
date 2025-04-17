package dding.timeManager.service.Studio;


import dding.timeManager.dto.request.Stodio.StudioTimeSlotRequest;
import dding.timeManager.entity.Studio.StudioTimeSlot;
import dding.timeManager.repository.StudioTimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudioTimeSlotService {

    private final StudioTimeSlotRepository studioTimeSlotRepository;

    public void saveStudioTimeSlot(String studioId, StudioTimeSlotRequest request) {
        List<StudioTimeSlot> slots = splitIntoSlots(studioId, request);
        studioTimeSlotRepository.saveAll(slots);
    }
    public void saveStudioTimeSlots(String studioId, List<StudioTimeSlotRequest> requests) {
        for(StudioTimeSlotRequest request : requests)
        {
            saveStudioTimeSlot(studioId,request);
        }
    }

    private List<StudioTimeSlot> splitIntoSlots(String studioId, StudioTimeSlotRequest request) {
        List<StudioTimeSlot> slots = new ArrayList<>();
        int start = request.getStartTime().getHour();
        int end = request.getEndTime().getHour();

        if (end <= start) {
            for (int hour = start; hour < 24; hour++) {
                slots.add(createSlot(studioId, request.getDate(), hour, request));
            }
            for (int hour = 0; hour < end; hour++) {
                slots.add(createSlot(studioId, request.getDate().plusDays(1), hour, request));
            }
        } else {
            for (int hour = start; hour < end; hour++) {
                slots.add(createSlot(studioId, request.getDate(), hour, request));
            }
        }
        return slots;
    }

    private StudioTimeSlot createSlot(String studioId, LocalDate date, int hour, StudioTimeSlotRequest request) {
        return StudioTimeSlot.builder()
                .studioId(studioId)
                .date(date)
                .hour(hour)
                .isClosed(request.isClosed())
                .specialPrice(request.getSpecialPrice())
                .build();
    }
}

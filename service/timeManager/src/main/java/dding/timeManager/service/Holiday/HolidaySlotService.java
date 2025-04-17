package dding.timeManager.service.Holiday;


import dding.timeManager.dto.request.Holiday.HolidaySlotRequest;
import dding.timeManager.entity.HolidatSlot.HolidaySlot;
import dding.timeManager.repository.HolidaySlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidaySlotService {

    private final HolidaySlotRepository holidaySlotRepository;

    public void saveHolidaySlot( HolidaySlotRequest request) {
        List<HolidaySlot> slots = splitIntoSlots(request);
        holidaySlotRepository.saveAll(slots);
    }
    public void saveHolidaySlots( List<HolidaySlotRequest> requests) {
        for(HolidaySlotRequest request : requests)
        {
            saveHolidaySlot(request);
        }
    }



    private List<HolidaySlot> splitIntoSlots(HolidaySlotRequest request) {
        List<HolidaySlot> slots = new ArrayList<>();
        int start = request.getStartTime().getHour();
        int end = request.getEndTime().getHour();

        if (end <= start) {
            for (int hour = start; hour < 24; hour++) {
                slots.add(createSlot( request.getDate(), hour, request));
            }
            for (int hour = 0; hour < end; hour++) {
                slots.add(createSlot( request.getDate().plusDays(1), hour, request));
            }
        } else {
            for (int hour = start; hour < end; hour++) {
                slots.add(createSlot(request.getDate(), hour, request));
            }
        }
        return slots;
    }

    private HolidaySlot createSlot(LocalDate date, int hour, HolidaySlotRequest request) {
        return HolidaySlot.builder()
                .bandRoomId(request.getBandRoomId())
                .studioId(request.getStudioId())
                .date(date)
                .hour(hour)
                .isClosed(request.isClosed())
                .specialPrice(request.getSpecialPrice())
                .build();
    }
}

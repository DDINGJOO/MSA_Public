package dding.timeManager.service.BandRoom;

import dding.timeManager.dto.request.BandRoom.BandRoomWeekRequest;
import dding.timeManager.dto.request.BandRoom.UpdateBandRoomWeekRequest;
import dding.timeManager.entity.BandRoom.BandRoomWeek;
import dding.timeManager.repository.BandRoomWeekRepository;

import dding.timeManager.service.Studio.StudioWeekService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BandRoomWeekService {

    private final BandRoomWeekRepository bandRoomWeekRepository;

    public void saveBandRoomWeek(String bandRoomId, BandRoomWeekRequest request) {
        // 2. 새로운 슬롯 생성
        List<BandRoomWeek> slots = splitIntoSlots(bandRoomId, request);
        bandRoomWeekRepository.saveAll(slots);
    }

    private List<BandRoomWeek> splitIntoSlots(String bandRoomId, BandRoomWeekRequest request) {
        List<BandRoomWeek> slots = new ArrayList<>();
        int start = request.getStartTime().getHour();
        int end = request.getEndTime().getHour();
        int dayOfWeek = request.getDayOfWeek();

        if (end <= start) { // Cross-day (ex. 23:00~02:00)
            for (int hour = start; hour < 24; hour++) {
                slots.add(createSlot(bandRoomId, dayOfWeek, hour, request));
            }
            int nextDay = (dayOfWeek + 1) % 7;
            for (int hour = 0; hour < end; hour++) {
                slots.add(createSlot(bandRoomId, nextDay, hour, request));
            }
        } else {
            for (int hour = start; hour < end; hour++) {
                slots.add(createSlot(bandRoomId, dayOfWeek, hour, request));
            }
        }
        return slots;
    }

    private BandRoomWeek createSlot(String bandRoomId, int dayOfWeek, int hour, BandRoomWeekRequest request) {
        return BandRoomWeek.builder()
                .bandRoomId(bandRoomId)
                .dayOfWeek(dayOfWeek)
                .hour(hour)
                .recurrencePattern(request.getRecurrencePattern())
                .isOddWeek(request.getIsOddWeek())
                .isClosed(request.isClosed())
                .specialPrice(request.getSpecialPrice())
                .build();
    }



    @Transactional
    public void saveBandRoomWeeks(String bandRoomId, List<BandRoomWeekRequest> requests) {
        for (BandRoomWeekRequest request : requests) {
            bandRoomWeekRepository.deleteByBandRoomIdAndDayOfWeek(bandRoomId, request.getDayOfWeek());
            saveBandRoomWeek(bandRoomId, request);
        }
    }

    public void updateBandRoomWeeks(String bandRoomId, List<BandRoomWeekRequest> requests) {


        // 1. 기존 BandRoom 주간 슬롯 삭제
        bandRoomWeekRepository.deleteByBandRoomId(bandRoomId);


        // 2. 새 BandRoom 주간 슬롯 저장
        saveBandRoomWeeks(bandRoomId, requests);

    }



    public boolean isOpenCheck(String bandRoomId, LocalDate date, LocalTime time){
        int dayOfWeek = date.getDayOfWeek().getValue() % 7; // (월=1 ~ 일=7) ➔ (일=0 ~ 토=6)
        int hour = (time != null) ? time.getHour() : -1;
        BandRoomWeek bandRoomWeek = bandRoomWeekRepository.findByBandRoomIdAndDayOfWeekAndHour(bandRoomId, dayOfWeek, hour);

        return bandRoomWeek != null && !bandRoomWeek.isClosed();
    }
}

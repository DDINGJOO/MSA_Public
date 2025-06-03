package dding.timeManager.service.Studio;

import dding.timeManager.dto.request.Stodio.StudioWeeksDto;
import dding.timeManager.entity.BandRoom.BandRoomWeek;
import dding.timeManager.entity.Studio.StudioWeek;
import dding.timeManager.repository.BandRoomWeekRepository;
import dding.timeManager.repository.StudioWeekRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudioWeekService {

    private final StudioWeekRepository studioWeekRepository;
    private final BandRoomWeekRepository bandRoomWeekRepository;

    @Transactional
    public void initStudioWeeksFromBandRoom(String bandRoomId, String studioId) {
        // 1. 기존 studio 시간 데이터 삭제
        if(studioWeekRepository.existsByStudioId(studioId))
        {
            studioWeekRepository.deleteAllByStudioId(studioId);
        }

        List<StudioWeek> studioWeeks = transformBandRoomWeeksToStudioWeeks(bandRoomId,studioId);
        // 4. 저장
        studioWeekRepository.saveAll(studioWeeks);
        System.out.println(studioWeekRepository.findAllByStudioId(studioId).size());
    }


    @Transactional
    public void upDateStudioWeeks(String studioId,String bandRoomId, List<StudioWeeksDto> requests) {
        if(studioWeekRepository.existsByStudioId(studioId))
        {
            studioWeekRepository.deleteAllByStudioId(studioId);
            initStudioWeeksFromBandRoom(studioId, bandRoomId);
        }

        for (StudioWeeksDto request : requests) {
            saveStudioWeek(studioId, request);
        }
    }

    @Transactional
    public void  upDateStudioWeeksFromClient(String studioId, List<StudioWeeksDto> requests) {
        if(studioWeekRepository.existsByStudioId(studioId))
        {
            studioWeekRepository.deleteAllByStudioIdAndOverriddenIsTrue(studioId);
        }

        for (StudioWeeksDto request : requests) {
            saveStudioWeek(studioId, request);
        }
    }












    @Transactional
    public void saveStudioWeek(String studioId, StudioWeeksDto request) {
        System.out.println("saveStudioWeek called");
        int dayOfWeek = request.getDayOfWeek();
        LocalTime startTime = request.getStartTime();
        LocalTime endTime = request.getEndTime();


        // 2. 요청 시간 범위 계산
        int start = startTime.getHour();
        int end = endTime.equals(LocalTime.MIDNIGHT) ? 24 : endTime.getHour();

        List<Integer> openHoursToday = new ArrayList<>();
        List<Integer> openHoursNextDay = new ArrayList<>();

        if (end <= start) {
            for (int h = start; h < 24; h++) openHoursToday.add(h);
            for (int h = 0; h < end; h++) openHoursNextDay.add(h);
        } else {
            for (int h = start; h < end; h++) openHoursToday.add(h);
        }

        // 3. 당일 0~23시 생성
        List<StudioWeek> slots = new ArrayList<>();
        {
            for (int hour : openHoursToday) {
                studioWeekRepository.deleteByStudioIdAndDayOfWeekAndHourAndOverriddenIsFalse(studioId, dayOfWeek, hour);
                slots.add(createSlot(studioId, dayOfWeek, hour, request));
            }
            if (!openHoursNextDay.isEmpty()) {
                int nextDay = (dayOfWeek + 1) % 7;
                for (int hour : openHoursNextDay) {
                    studioWeekRepository.deleteByStudioIdAndDayOfWeekAndHourAndOverriddenIsFalse(studioId, dayOfWeek, hour);
                    slots.add(createSlot(studioId, nextDay, hour, request));
                }
            }
        }

        slots.forEach(s -> System.out.println(
                s.getDayOfWeek() + " " + s.getHour() +
                        " closed=" + s.isClosed() +
                        " overridden=" + s.getOverridden() +
                        " specialPrice=" + s.getSpecialPrice()
        ));

        studioWeekRepository.saveAll(slots);
        System.out.println(studioWeekRepository.findAllByStudioId(studioId).size());

    }






    private StudioWeek createSlot(String studioId, int dayOfWeek, int hour, StudioWeeksDto request) {
        return StudioWeek.builder()
                .studioId(studioId)
                .dayOfWeek(dayOfWeek)
                .hour(hour)
                .recurrencePattern(request.getRecurrencePattern())
                .isOddWeek(request.getIsOddWeek())
                .isClosed(request.isClosed())
                .overridden(true)
                .specialPrice(request.getSpecialPrice())
                .build();
    }


    private List<StudioWeek> transformBandRoomWeeksToStudioWeeks(String bandRoomId, String studioId) {
        // 1. bandRoomId 기반으로 BandRoomWeek 리스트 조회
        List<BandRoomWeek> bandRoomWeeks = bandRoomWeekRepository.findAllByBandRoomId(bandRoomId);
        // 2. BandRoomWeek → StudioWeek 변환
        return bandRoomWeeks.stream()
                .map(bw -> StudioWeek.builder()
                        .studioId(studioId)
                        .dayOfWeek(bw.getDayOfWeek())
                        .hour(bw.getHour())
                        .isClosed(bw.isClosed())
                        .recurrencePattern(bw.getRecurrencePattern())
                        .isOddWeek(bw.getIsOddWeek())
                        .specialPrice(bw.getSpecialPrice())
                        .overridden(false)
                        .build()
                ).toList();
    }

    public List<StudioWeek> getStudioWeeks(String studioId) {
        List<StudioWeek> studioWeeks = studioWeekRepository.findAllByStudioId(studioId);
        if (studioWeeks.isEmpty()) {
            System.out.println("No studio weeks found for studioId: " + studioId);
        } else {
            System.out.println("Found " + studioWeeks.size() + " studio weeks for studioId: " + studioId);
        }
        return studioWeeks;
    }
}

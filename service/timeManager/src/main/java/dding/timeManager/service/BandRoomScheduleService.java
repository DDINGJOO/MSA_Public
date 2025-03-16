package dding.timeManager.service;



import dding.timeManager.dto.request.BandRoomScheduleRequest;
import dding.timeManager.dto.response.BandRoomScheduleResponse;
import dding.timeManager.entity.BandRoomSchedule;
import dding.timeManager.repository.BandRoomScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BandRoomScheduleService {

    private final BandRoomScheduleRepository bandRoomScheduleRepository;

    public void save(BandRoomScheduleRequest request) {
        BandRoomSchedule schedule = BandRoomSchedule.builder()
                .bandRoomId(request.getBandRoomId())
                .date(LocalDate.parse(request.getDate()))
                .hour(request.getHour())
                .available(request.isAvailable())
                .memo(request.getMemo())
                .build();
        bandRoomScheduleRepository.save(schedule);
    }

    public List<BandRoomScheduleResponse> findByBandRoomAndDateRange(Long bandRoomId, String fromDate, String toDate) {
        LocalDate from = LocalDate.parse(fromDate);
        LocalDate to = LocalDate.parse(toDate);

        List<BandRoomSchedule> list = bandRoomScheduleRepository.findByBandRoomIdAndDateBetween(bandRoomId, from, to);
        return list.stream()
                .map(schedule -> new BandRoomScheduleResponse(
                        schedule.getDate().toString(),
                        schedule.getHour(),
                        schedule.isAvailable(),
                        schedule.getMemo()
                )).collect(Collectors.toList());
    }

    public void deleteByBandRoomAndDate(Long bandRoomId, String date) {
        bandRoomScheduleRepository.deleteByBandRoomIdAndDate(bandRoomId, LocalDate.parse(date));
    }
}
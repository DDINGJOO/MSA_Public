package dding.timeManager.service;

import dding.timeManager.dto.request.BandRoomScheduleRequest;
import dding.timeManager.dto.response.BandRoomScheduleResponse;
import dding.timeManager.entity.user.BandRoomSchedule;
import dding.timeManager.repository.BandRoomScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BandRoomScheduleService {

    private final BandRoomScheduleRepository repository;

    public void addSchedule(BandRoomScheduleRequest request) {
        BandRoomSchedule schedule = BandRoomSchedule.builder()
                .roomId(request.getRoomId())
                .dayOfWeek(request.getDayOfWeek())
                .hour(request.getHour())
                .date(request.getDate())
                .available(request.isAvailable())
                .build();

        repository.save(schedule);
    }

    public List<BandRoomScheduleResponse> getSchedulesByRoomAndDate(String roomId, java.time.LocalDate date) {
        return repository.findByRoomIdAndDate(roomId, date)
                .stream()
                .map(schedule -> BandRoomScheduleResponse.builder()
                        .id(schedule.getId())
                        .dayOfWeek(schedule.getDayOfWeek())
                        .hour(schedule.getHour())
                        .roomId(schedule.getRoomId())
                        .date(schedule.getDate())
                        .available(schedule.isAvailable())
                        .build())
                .collect(Collectors.toList());
    }

    public void deleteSchedule(Long id) {
        repository.deleteById(id);
    }
}

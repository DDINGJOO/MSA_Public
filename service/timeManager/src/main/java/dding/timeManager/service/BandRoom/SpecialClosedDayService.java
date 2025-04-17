package dding.timeManager.service.BandRoom;

import dding.timeManager.dto.request.SpecialClosedDayRequest;
import dding.timeManager.dto.response.SpecialClosedDayResponse;
import dding.timeManager.entity.BandRoom.SpecialClosedDay;
import dding.timeManager.repository.SpecialClosedDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialClosedDayService {

    private final SpecialClosedDayRepository specialClosedDayRepository;

    @Transactional
    public String create(String bandRoomId, SpecialClosedDayRequest request) {
        SpecialClosedDay closedDay = SpecialClosedDay.builder()
                .id(UUID.randomUUID().toString())
                .bandRoomId(bandRoomId)
                .date(LocalDate.parse(request.getDate()))
                .reason(request.getReason())
                .build();

        specialClosedDayRepository.save(closedDay);
        return closedDay.getId();
    }

    @Transactional(readOnly = true)
    public List<SpecialClosedDayResponse> getByBandRoom(String bandRoomId) {
        return specialClosedDayRepository.findByBandRoomId(bandRoomId).stream()
                .map(day -> SpecialClosedDayResponse.builder()
                        .id(day.getId())
                        .date(day.getDate().toString())
                        .reason(day.getReason())
                        .build())
                .toList();
    }

    @Transactional
    public void delete(String id) {
        specialClosedDayRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllByBandRoom(String bandRoomId) {
        specialClosedDayRepository.deleteAllByBandRoomId(bandRoomId);
    }


}

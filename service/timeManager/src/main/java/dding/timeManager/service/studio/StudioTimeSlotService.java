package dding.timeManager.service.studio;

import dding.timeManager.dto.request.studio.StudioTimeSlotRequest;
import dding.timeManager.dto.response.studio.StudioTimeSlotResponse;
import dding.timeManager.entity.StudioTimeSlot;
import dding.timeManager.repository.StudioTimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudioTimeSlotService {

    private final StudioTimeSlotRepository studioTimeSlotRepository;

    /**
     * 스튜디오 시간 슬롯 저장
     * startTime ~ endTime 범위 입력을 받아서
     * 내부에서는 1시간 단위로 쪼개서 저장
     */
    @Transactional
    public void saveAll(String studioId, List<StudioTimeSlotRequest> requests) {
        List<StudioTimeSlot> slots = requests.stream()
                .flatMap(req -> {
                    int startHour = req.getStartTime().getHour();
                    int endHour = req.getEndTime().getHour();

                    // 24시 넘어가는 경우 처리
                    if (endHour == 0) {
                        endHour = 24;
                    }

                    return java.util.stream.IntStream.range(startHour, endHour)
                            .mapToObj(hour -> StudioTimeSlot.builder()
                                    .studioId(studioId)
                                    .date(req.getDate())
                                    .dayOfWeek(req.getDayOfWeek())
                                    .hour(hour)
                                    .isClosed(req.isClosed())
                                    .price(req.getPrice())
                                    .isOddWeek(req.getIsOddWeek())
                                    .recurrencePattern(req.getRecurrencePattern())
                                    .build());
                })
                .toList();

        studioTimeSlotRepository.saveAll(slots);
    }

    /**
     * 스튜디오 시간 슬롯 조회
     */
    @Transactional(readOnly = true)
    public List<StudioTimeSlotResponse> getAllByStudio(String studioId) {
        return studioTimeSlotRepository.findByStudioId(studioId).stream()
                .map(slot -> StudioTimeSlotResponse.builder()
                        .id(slot.getId())
                        .studioId(slot.getStudioId())
                        .date(slot.getDate())
                        .dayOfWeek(slot.getDayOfWeek())
                        .hour(slot.getHour())
                        .isClosed(slot.isClosed())
                        .price(slot.getPrice())
                        .isOddWeek(slot.getIsOddWeek())
                        .recurrencePattern(slot.getRecurrencePattern())
                        .build())
                .toList();
    }

    /**
     * 스튜디오 시간 슬롯 수정
     * - 기존 슬롯 모두 삭제 후 새로 저장
     */
    @Transactional
    public void updateTimeSlots(String studioId, List<StudioTimeSlotRequest> requests) {
        studioTimeSlotRepository.deleteByStudioId(studioId);
        saveAll(studioId, requests);
    }

    /**
     * 개별 시간 슬롯 삭제
     */
    @Transactional
    public void delete(Long timeSlotId) {
        studioTimeSlotRepository.deleteById(timeSlotId);
    }

    /**
     * 스튜디오 전체 시간 슬롯 삭제
     */
    @Transactional
    public void deleteAllByStudio(String studioId) {
        studioTimeSlotRepository.deleteByStudioId(studioId);
    }
}

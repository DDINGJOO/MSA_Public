package dding.timeManager.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BandRoomScheduleRequest {
    private int dayOfWeek;         // 요일 (0~6)
    private int hour;              // 시간 (0~23)
    private String roomId;         // 밴드룸 ID
    private LocalDate date;        //  날짜
    private boolean available;     // 예약 가능 여부
}
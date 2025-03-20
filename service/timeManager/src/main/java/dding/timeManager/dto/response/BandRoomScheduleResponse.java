package dding.timeManager.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BandRoomScheduleResponse {
    private Long id;
    private int dayOfWeek;
    private int hour;
    private String roomId;
    private LocalDate date;
    private boolean available;
}
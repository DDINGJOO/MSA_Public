package dding.timeManager.entity.BandRoom;


import dding.timeManager.config.RecurrencePattern;
import dding.timeManager.entity.AbstractTimeSlot;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Entity
@Table(name = "bandroom_time_slot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BandRoomTimeSlot extends AbstractTimeSlot {

    @Column(nullable = false)
    private String bandRoomId;  // 밴드룸 ID (String으로 관리)

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecurrencePattern recurrencePattern; // WEEKLY, BIWEEKLY, MONTHLY

    @Column(nullable = false)
    private Boolean isClosed;

    @Column
    private Boolean isOddWeek; // 격주일 경우 홀/짝 여부 (nullable 가능)

}

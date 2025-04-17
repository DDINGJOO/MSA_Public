package dding.timeManager.entity.BandRoom;

import dding.timeManager.config.RecurrencePattern;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BandRoomWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bandRoomId; // 합주실 ID

    @Column(nullable = false)
    private int dayOfWeek; // 0(일) ~ 6(토)

    @Column(nullable = false)
    private int hour; // 0 ~ 23

    @Enumerated(EnumType.STRING)
    private RecurrencePattern recurrencePattern; // WEEKLY, BIWEEKLY

    private Boolean isOddWeek; // 홀수주 or 짝수주 구분 (BIWEEKLY용)

    @Column(nullable = false)
    private boolean isClosed; // 휴무 여부

    private Integer specialPrice; // 특별가격 (nullable)
}

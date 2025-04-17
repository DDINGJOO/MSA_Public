package dding.timeManager.entity;

import dding.timeManager.config.RecurrencePattern;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudioTimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studioId; // 소속 스튜디오 ID

    // 날짜 단위 슬롯이면 사용
    private LocalDate date;

    // 주간 단위 슬롯이면 사용 (0: 일 ~ 6: 토)
    private Integer dayOfWeek;

    // 시간 (몇 시)
    @Column(nullable = false)
    private Integer hour;

    // true면 예약 차단
    @Column(nullable = false)
    private boolean isClosed;

    // 이 시간 슬롯에서만 적용할 별도 가격 (null 가능)
    private Integer price;

    // 홀수주만 적용할지 여부 (BIWEEKLY에만 사용)
    private Boolean isOddWeek;

    // 반복 주기 (WEEKLY / BIWEEKLY / MONTHLY)
    @Enumerated(EnumType.STRING)
    private RecurrencePattern recurrencePattern;
}

package dding.timeManager.entity;




import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "bandroom_schedule")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BandRoomSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bandRoomId; // 합주실 참조 ID

    private LocalDate date; // 특정 날짜 (YYYY-MM-DD)

    private int hour; // 0 ~ 23

    private boolean available;

    private String memo; // optional 설명 (예: 휴무, 외부 대관 등)
}
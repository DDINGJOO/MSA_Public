package dding.timeManager.entity;



import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;

@Entity
@Table(name = "user_time_schedule")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;  // 유저 참조 (FK는 따로 연결하지 않아도 됨)

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek; // MON ~ SUN

    private int hour; // 0 ~ 23

    private boolean available;
}


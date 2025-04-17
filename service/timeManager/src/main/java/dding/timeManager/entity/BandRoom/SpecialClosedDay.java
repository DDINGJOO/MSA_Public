package dding.timeManager.entity.BandRoom;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "special_closed_day")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecialClosedDay {

    @Id
    private String id;

    @Column(nullable = false)
    private String bandRoomId; // 밴드룸 ID

    @Column(nullable = false)
    private LocalDate date;    // 휴무 날짜

    @Column
    private String reason;     // 휴무 사유 (ex: 점검, 임시 휴업 등)
}

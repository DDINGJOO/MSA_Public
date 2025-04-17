package dding.timeManager.entity.HolidatSlot;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HolidaySlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date; // 공휴일 날짜

    @Column(nullable = false)
    private String studioId;

    @Column(nullable = false)
    private String bandRoomId;

    @Column(nullable = false)
    private int hour;

    @Column(nullable = false)
    private boolean isClosed;

    private Integer specialPrice;
}


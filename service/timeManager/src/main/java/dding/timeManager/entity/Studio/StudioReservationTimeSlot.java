package dding.timeManager.entity.Studio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name = "studio_reservation_slot")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudioReservationTimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studioId;

    private LocalDate date;

    private int hour; // 0 ~ 23

    private String reservationId; // 외부 예약 시스템에서 생성된 예약 ID

    private LocalDateTime createdAt;
}

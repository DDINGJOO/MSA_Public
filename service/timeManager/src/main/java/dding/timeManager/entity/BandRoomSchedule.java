package dding.timeManager.entity;


import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BandRoomSchedule extends AbstractTimeSlot {

    @Column(name = "band_room_id")
    private String roomId;

    private LocalDate date; // 특정 날짜
    private boolean available;
}
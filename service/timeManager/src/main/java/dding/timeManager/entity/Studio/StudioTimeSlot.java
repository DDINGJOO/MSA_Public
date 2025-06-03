package dding.timeManager.entity.Studio;

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
public class StudioTimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studioId;

    @Column(nullable = false)
    private LocalDate date; // yyyy-MM-dd

    @Column(nullable = false)
    private int hour; // 0~23

    @Column(nullable = false)
    private boolean isClosed;

    private Integer specialPrice;

}

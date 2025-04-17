package dding.timeManager.entity.Studio;

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
public class StudioWeek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String studioId;

    @Column(nullable = false)
    private int dayOfWeek;

    @Column(nullable = false)
    private int hour;

    @Enumerated(EnumType.STRING)
    private RecurrencePattern recurrencePattern;

    private Boolean isOddWeek;

    @Column(nullable = false)
    private boolean isClosed;

    private Integer specialPrice;
}

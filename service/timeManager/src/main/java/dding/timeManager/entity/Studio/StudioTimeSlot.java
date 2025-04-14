package dding.timeManager.entity.Studio;

import dding.timeManager.entity.AbstractTimeSlot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "studio_weekly_time_slot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StudioTimeSlot extends AbstractTimeSlot {

    @Column(nullable = false)
    private String studioId;

    private boolean available;

    private boolean isHoliday; // 공휴일 전용 운영 시간인지 여부
}

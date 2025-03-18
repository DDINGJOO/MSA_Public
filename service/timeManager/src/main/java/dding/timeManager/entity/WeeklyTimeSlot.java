package dding.timeManager.entity;



import dding.timeManager.config.TimeSlot;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "weekly_time_schedule")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyTimeSlot {

    @Id
    private String id;

    private String objectId;  // 해당 객체 참조 (FK는 따로 연결하지 않아도 됨)

    private int dayOfWeek;
    private int hour;
    private boolean available;

    public void create(TimeSlot timeSlot,boolean available)
    {
        this.dayOfWeek = timeSlot.getDayOfWeek();
        this.hour = timeSlot.getHour();
        this.available = available;
    }
}


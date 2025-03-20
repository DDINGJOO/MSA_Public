package dding.timeManager.entity;



import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user_time_schedule")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserTimeSlot extends AbstractTimeSlot {

    private String userId;
    private boolean available;
}
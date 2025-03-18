package dding.timeManager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "common_time_slot")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommonTimeSlots {
    @Id
    String id;


    String ObjectId;

    int dayOfWeek;
    int hour;
}

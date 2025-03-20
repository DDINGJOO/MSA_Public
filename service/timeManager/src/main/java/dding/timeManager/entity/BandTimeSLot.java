package dding.timeManager.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "band_time_schedule")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BandTimeSLot extends AbstractTimeSlot {

    private String bandId;
    private boolean available;
}
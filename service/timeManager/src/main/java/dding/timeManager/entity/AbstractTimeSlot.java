package dding.timeManager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass // 상속만 위한 부모 클래스라면 @MappedSuperclass 도 가능
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class AbstractTimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int dayOfWeek;
    private int hour;

    public AbstractTimeSlot(int dayOfWeek, int hour) {
        this.dayOfWeek = dayOfWeek;
        this.hour = hour;
    }
}
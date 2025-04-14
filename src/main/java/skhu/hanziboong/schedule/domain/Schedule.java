package skhu.hanziboong.schedule.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import skhu.hanziboong.global.BaseEntity;
import skhu.hanziboong.house.domain.House;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    @OneToOne(mappedBy = "schedule", fetch = FetchType.LAZY)
    private House house;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ScheduleParticipant> participants;

    @Builder
    public Schedule(String title, String description,
                    LocalDateTime startAt, LocalDateTime endAt,
                    House house, Set<ScheduleParticipant> participants) {
        this.title = title;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
        this.house = house;
        this.participants = participants;
    }
}

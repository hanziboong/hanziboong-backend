package skhu.hanziboong.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skhu.hanziboong.schedule.domain.ScheduleParticipant;

public interface ScheduleParticipantRepository extends JpaRepository<ScheduleParticipant, Long> {
}

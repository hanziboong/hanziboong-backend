package skhu.hanziboong.schedule.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skhu.hanziboong.schedule.domain.Schedule;
import skhu.hanziboong.schedule.domain.ScheduleParticipant;

@Repository
public interface ScheduleParticipantRepository extends JpaRepository<ScheduleParticipant, Long> {
    
    List<ScheduleParticipant> findAllBySchedule(Schedule schedule);
}

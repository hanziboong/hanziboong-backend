package skhu.hanziboong.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skhu.hanziboong.schedule.domain.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}

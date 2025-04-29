package skhu.hanziboong.schedule.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.schedule.domain.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByHouseAndStartAtBetween(House house, LocalDateTime start, LocalDateTime end);
}

package skhu.hanziboong.schedule.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.schedule.domain.Schedule;
import skhu.hanziboong.schedule.domain.ScheduleParticipant;

public record ScheduleRequest(
        Long houseId,
        String title,
        String description,
        LocalDateTime startAt,
        LocalDateTime endAt,
        List<Long> participantUserId
) {

    public Schedule toSchedule (House house) {
        return new Schedule(title, description, startAt, endAt, house);
    }
}

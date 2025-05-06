package skhu.hanziboong.schedule.dto.response;

import skhu.hanziboong.schedule.domain.Schedule;

public record ScheduleCreateResponse(
        Long id
) {
    public static ScheduleCreateResponse from(Schedule schedule) {
        return new ScheduleCreateResponse(schedule.getId());
    }
}

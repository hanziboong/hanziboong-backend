package skhu.hanziboong.schedule.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import skhu.hanziboong.schedule.domain.Schedule;
import skhu.hanziboong.schedule.domain.ScheduleParticipant;

@Builder
public record ScheduleResponse(
        Long id,
        String title,
        String description,
        LocalDateTime startAt,
        LocalDateTime endAt,
        List<String> participants
) {
    public static ScheduleResponse from(Schedule schedule, List<ScheduleParticipant> participants) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .description(schedule.getDescription())
                .startAt(schedule.getStartAt())
                .endAt(schedule.getEndAt())
                .participants(getNames(participants))
                .build();
    }

    private static List<String> getNames(List<ScheduleParticipant> participants) {
        return participants.stream()
                .map(ScheduleParticipant::getMemberName)
                .toList();
    }
}

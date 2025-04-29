package skhu.hanziboong.schedule.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.hanziboong.global.exception.CustomException;
import skhu.hanziboong.global.exception.ErrorCode;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.house.repository.HouseRepository;
import skhu.hanziboong.member.domain.Member;
import skhu.hanziboong.member.repository.MemberRepository;
import skhu.hanziboong.schedule.domain.Schedule;
import skhu.hanziboong.schedule.domain.ScheduleParticipant;
import skhu.hanziboong.schedule.dto.request.ScheduleRequest;
import skhu.hanziboong.schedule.dto.response.ScheduleCreateResponse;
import skhu.hanziboong.schedule.dto.response.ScheduleResponse;
import skhu.hanziboong.schedule.repository.ScheduleParticipantRepository;
import skhu.hanziboong.schedule.repository.ScheduleRepository;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleParticipantRepository scheduleParticipantRepository;
    private final MemberRepository memberRepository;
    private final HouseRepository houseRepository;

    @Transactional
    public ScheduleCreateResponse createSchedule(ScheduleRequest scheduleRequest) {
        House house = houseRepository.findById(scheduleRequest.houseId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_EXCEPTION,
                        ErrorCode.NOT_FOUND_HOUSE_EXCEPTION.getMessage()));

        Schedule schedule = scheduleRequest.toSchedule(house);
        scheduleRepository.save(schedule);

        Set<ScheduleParticipant> participants = getParticipants(schedule, scheduleRequest.participantUserId());
        scheduleParticipantRepository.saveAll(participants);

        return ScheduleCreateResponse.from(schedule);
    }

    private Set<ScheduleParticipant> getParticipants(Schedule schedule, List<Long> participantIds) {
        return participantIds.stream()
                .map(this::getMemberById)
                .map(member -> new ScheduleParticipant(schedule, member))
                .collect(Collectors.toSet());
    }

    private Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION,
                        ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> getSchedulesByHouseAndYearAndMonth(Long houseId, Integer year, Integer month) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_EXCEPTION,
                        ErrorCode.NOT_FOUND_HOUSE_EXCEPTION.getMessage()));

        List<Schedule> schedules = getSchedules(house, year, month);
        return createResponsesFrom(schedules);
    }

    private List<Schedule> getSchedules(House house, Integer year, Integer month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime end = LocalDateTime.of(yearMonth.atEndOfMonth(), LocalTime.MAX);

        return scheduleRepository.findByHouseAndStartAtBetween(house, start, end);
    }

    private List<ScheduleResponse> createResponsesFrom(List<Schedule> schedules) {
        List<ScheduleResponse> responses = new ArrayList<>();
        for (Schedule schedule : schedules) {
            List<ScheduleParticipant> participants = scheduleParticipantRepository.findAllBySchedule(schedule);
            responses.add(ScheduleResponse.from(schedule, participants));
        }

        return Collections.unmodifiableList(responses);
    }
}

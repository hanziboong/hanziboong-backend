package skhu.hanziboong.schedule.service;

import java.util.List;
import java.util.Optional;
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
import skhu.hanziboong.schedule.repository.ScheduleRepository;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final HouseRepository houseRepository;

    @Transactional
    public void createSchedule(ScheduleRequest scheduleRequest) {

        House house = houseRepository.findById(scheduleRequest.houseId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_EXCEPTION,
                        ErrorCode.NOT_FOUND_HOUSE_EXCEPTION.getMessage()));

        Schedule schedule = scheduleRequest.toSchedule(house);

        Set<ScheduleParticipant> participants = getParticipants(schedule, scheduleRequest.participantUserId());
        schedule.addParticipants(participants);

        scheduleRepository.save(schedule);
    }

    private Set<ScheduleParticipant> getParticipants(Schedule schedule, List<Long> participantIds) {
        return  participantIds.stream()
                .map(this::getMemberById)
                .map(member -> new ScheduleParticipant(schedule, member))
                .collect(Collectors.toSet());
    }

    private Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION,
                        ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage()));
    }
}

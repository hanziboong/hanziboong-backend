package skhu.hanziboong.schedule.controller;

import java.net.URI;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skhu.hanziboong.schedule.dto.request.ScheduleRequest;
import skhu.hanziboong.schedule.service.ScheduleService;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Void> createSchedule(@RequestBody ScheduleRequest request) {
        scheduleService.createSchedule(request);
        
        return ResponseEntity.created(URI.create("/api/schedule/" + request.houseId())).build();
    }
}

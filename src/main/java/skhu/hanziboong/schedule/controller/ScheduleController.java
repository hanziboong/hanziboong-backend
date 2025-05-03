package skhu.hanziboong.schedule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skhu.hanziboong.global.exception.model.BadRequestException;
import skhu.hanziboong.schedule.dto.request.ScheduleRequest;
import skhu.hanziboong.schedule.dto.response.ScheduleCreateResponse;
import skhu.hanziboong.schedule.dto.response.ScheduleResponse;
import skhu.hanziboong.schedule.service.ScheduleService;

@Tag(name = "일정")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "일정 생성")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "요청이 정상적으로 처리되었을 때"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "요청 Body에 올바르지 않은 값이 전달되었을 때",
                    content = @Content(schema = @Schema(implementation = BadRequestException.class))
            ),
    })
    @PostMapping
    public ResponseEntity<ScheduleCreateResponse> createSchedule(@RequestBody ScheduleRequest request) {
        ScheduleCreateResponse response = scheduleService.createSchedule(request);

        return ResponseEntity.created(URI.create("/api/schedules/" + response.id())).build();
    }

    @Operation(summary = "집 ID, 연도, 월 기준 일정 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "요청이 정상적으로 처리되었을 때"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "존재하지 않는 집 ID로 요청했을 때",
                    content = @Content(schema = @Schema(implementation = BadRequestException.class))
            ),
    })
    @GetMapping("/house/{houseId}")
    public ResponseEntity<List<ScheduleResponse>> findSchedulesByHouseAndYearAndMonth(
            @PathVariable Long houseId,
            @RequestParam("year") Integer year,
            @RequestParam("month") Integer month
    ) {
        return ResponseEntity.ok(scheduleService.getSchedulesByHouseAndYearAndMonth(houseId, year, month));
    }
}

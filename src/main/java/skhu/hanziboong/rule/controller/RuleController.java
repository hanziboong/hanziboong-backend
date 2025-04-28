package skhu.hanziboong.rule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skhu.hanziboong.global.exception.model.BadRequestException;
import skhu.hanziboong.global.exception.model.UnAuthorizedException;
import skhu.hanziboong.rule.dto.request.RuleRequest;
import skhu.hanziboong.rule.dto.response.RuleCreateResponse;
import skhu.hanziboong.rule.dto.response.RuleResponse;
import skhu.hanziboong.rule.service.RuleService;

@Tag(name = "규칙")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rules")
public class RuleController {

    private final RuleService ruleService;

    @Operation(summary = "규칙 생성")
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
    public ResponseEntity<Void> createRule(
            @RequestBody RuleRequest request
    ) {
        RuleCreateResponse response = ruleService.createRule(request);

        return ResponseEntity.created(URI.create("/api/rules/" + response.id())).build();
    }

    @Operation(summary = "ID 기준 규칙 단건 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "요청이 정상적으로 처리되었을 때"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "존재하지 않는 규칙 ID로 요청했을 때",
                    content = @Content(schema = @Schema(implementation = BadRequestException.class))
            ),
    })
    @GetMapping("/{id}")
    public ResponseEntity<RuleResponse> findRule(@PathVariable Long id) {
        return ResponseEntity.ok(ruleService.findRuleById(id));
    }

    @Operation(summary = "집 ID 기준 전체 규칙 목록 조회")
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
    @GetMapping("/house/{id}")
    public ResponseEntity<Page<RuleResponse>> findRulesByHouse(
            @PathVariable
            Long id,
            @Parameter(hidden = true)
            @PageableDefault(size = 5, sort = "id", direction = Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(ruleService.findRulesByHouseId(id, pageable));
    }

    // TODO: 로그인 구현 후, 인증/인가 예외처리 필요
    @Operation(summary = "규칙 수정")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "요청이 정상적으로 처리되었을 때"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "요청 Body에 올바르지 않은 값이 전달되었을 때",
                    content = @Content(schema = @Schema(implementation = BadRequestException.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "작성자가 아닌 사용자가 요청했을 때",
                    content = @Content(schema = @Schema(implementation = UnAuthorizedException.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRule(
            @PathVariable Long id,
            @RequestBody RuleRequest request
    ) {
        ruleService.updateRuleById(id, request);

        return ResponseEntity.ok().build();
    }

    // TODO: 로그인 구현 후, 인증/인가 예외처리 필요
    @Operation(summary = "규칙 삭제")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "요청이 정상적으로 처리되었을 때"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "존재하지 않는 여행기 ID로 요청했을 때",
                    content = @Content(schema = @Schema(implementation = BadRequestException.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "작성자가 아닌 사용자가 요청했을 때",
                    content = @Content(schema = @Schema(implementation = UnAuthorizedException.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        ruleService.deleteRuleById(id);

        return ResponseEntity.noContent()
                .build();
    }
}

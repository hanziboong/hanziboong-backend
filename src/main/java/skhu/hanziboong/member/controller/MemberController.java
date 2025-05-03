package skhu.hanziboong.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skhu.hanziboong.global.exception.model.BadRequestException;
import skhu.hanziboong.member.dto.request.MemberRequest;
import skhu.hanziboong.member.dto.response.MemberCreateResponse;
import skhu.hanziboong.member.dto.response.MemberResponse;
import skhu.hanziboong.member.service.MemberService;

@Tag(name = "사용자")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "사용자 생성")
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
    public ResponseEntity<MemberCreateResponse> createMember(MemberRequest request) {
        MemberCreateResponse response = memberService.createMember(request);

        return ResponseEntity.created(URI.create("/api/members/" + response.id())).build();
    }

    @Operation(summary = "ID 기준 사용자 단건 상세 조회")
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
    public ResponseEntity<MemberResponse> findMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.findMemberById(id));
    }
}

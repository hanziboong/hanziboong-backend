package skhu.hanziboong.expense.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResponseExtractor;
import skhu.hanziboong.global.exception.model.BadRequestException;
import skhu.hanziboong.expense.dto.request.ExpenseRequest;
import skhu.hanziboong.expense.dto.response.ExpenseResponse;

@Tag(name = "가계부", description = "가계부 관리 API")
public interface ExpenseApiDocs {

    @Operation(summary = "공동 지출 내역 생성")
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
    ResponseEntity<Void> createExpense(ExpenseRequest request);

    @Operation(summary = "공동 지출 내역 단건 조회")
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
    ResponseEntity<ExpenseResponse> findExpense(Long id);

    @Operation(summary = "공동 지출 내역 전체 조회")
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
    ResponseEntity<Page<ExpenseResponse>> findExpensesByHouse(Long houseId, Pageable pageable);

    @Operation(summary = "개인 정산 유무 상태 수정")
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
    ResponseEntity<Boolean> settledByExpenseParticipantId(Long id, Boolean isSettled);

    @Operation(summary = "공동 지출 내역 수정")
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
    ResponseEntity<Void> updateExpenseDetails(Long id, ExpenseRequest request);

    @Operation(summary = "공동 지출 내역 단건 삭제")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "203",
                    description = "요청이 정상적으로 처리되었을 때"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "요청 Body에 올바르지 않은 값이 전달되었을 때",
                    content = @Content(schema = @Schema(implementation = BadRequestException.class))
            ),
    })
    ResponseEntity<Void> deleteExpense(Long id);
}

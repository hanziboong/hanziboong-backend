package skhu.hanziboong.ledger.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import skhu.hanziboong.global.exception.model.BadRequestException;
import skhu.hanziboong.ledger.dto.request.ExpenseRequest;

@Tag(name = "가계부", description = "가계부 관리 API")
public interface ExpenseApiDocs {

    @Operation(summary = "가계부 생성")
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
}

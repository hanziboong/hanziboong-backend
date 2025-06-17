package skhu.hanziboong.expense.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import skhu.hanziboong.expense.dto.request.ToBuyItemsRequest;
import skhu.hanziboong.expense.dto.response.ToBuyItemResponse;
import skhu.hanziboong.global.exception.model.BadRequestException;

@Tag(name = "사야 할 물건", description = "사야 할 물건 api")
public interface ToBuyItemApiDocs {

    @Operation(summary = "사야 할 물건 생성")
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
    ResponseEntity<Void> createToBuyItems(Long id, ToBuyItemsRequest request);

    ResponseEntity<ToBuyItemResponse> checkItem(Long id);

    ResponseEntity<List<ToBuyItemResponse>> getToBuyItems(Long id);

    ResponseEntity<Void> updateItemName(Long id, String name);

    ResponseEntity<Void> deleteItem(Long id);
}

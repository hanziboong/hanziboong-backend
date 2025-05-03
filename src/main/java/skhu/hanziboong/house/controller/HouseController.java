package skhu.hanziboong.house.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skhu.hanziboong.global.exception.model.BadRequestException;
import skhu.hanziboong.house.dto.request.HouseRequest;
import skhu.hanziboong.house.dto.response.HouseCreateResponse;
import skhu.hanziboong.house.service.HouseService;

@Tag(name = "집")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/houses")
public class HouseController {

    private final HouseService houseService;

    @Operation(summary = "집 생성")
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
    public ResponseEntity<HouseCreateResponse> createHouse(HouseRequest request) {
        HouseCreateResponse response = houseService.createHouse(request);

        return ResponseEntity.created(URI.create("/api/houses/" + response.id())).build();
    }
}

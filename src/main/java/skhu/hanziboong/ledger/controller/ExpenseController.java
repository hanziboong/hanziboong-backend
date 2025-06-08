package skhu.hanziboong.ledger.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skhu.hanziboong.ledger.controller.docs.ExpenseApiDocs;
import skhu.hanziboong.ledger.dto.request.ExpenseRequest;
import skhu.hanziboong.ledger.dto.response.ExpenseResponse;
import skhu.hanziboong.ledger.service.ExpenseService;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("api/expense")
public class ExpenseController implements ExpenseApiDocs {

    private final ExpenseService expenseService;

    @Override
    @PostMapping
    public ResponseEntity<Void> createExpense(@RequestBody ExpenseRequest request) {
        ExpenseResponse response = expenseService.createExpense(request);

        return ResponseEntity.created(URI.create("api/expense" + response.id())).build();
    }

    @Override
    @GetMapping("/{houseId}")
    public ResponseEntity<Page<ExpenseResponse>> findExpensesByHouse(
            @PathVariable
            Long houseId,
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC)
            Pageable pageable) {

            Page<ExpenseResponse> responses = expenseService.findExpensesByHouseId(houseId, pageable);

        return ResponseEntity.ok(responses);
    }
}

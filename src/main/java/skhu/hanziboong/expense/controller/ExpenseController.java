package skhu.hanziboong.expense.controller;

import io.swagger.v3.oas.annotations.Parameter;
import java.net.URI;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skhu.hanziboong.expense.controller.docs.ExpenseApiDocs;
import skhu.hanziboong.expense.dto.request.ExpenseRequest;
import skhu.hanziboong.expense.dto.response.ExpenseResponse;
import skhu.hanziboong.expense.service.ExpenseService;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/expense")
public class ExpenseController implements ExpenseApiDocs {

    private final ExpenseService expenseService;

    @Override
    @PostMapping
    public ResponseEntity<Void> createExpense(@RequestBody ExpenseRequest request) {
        ExpenseResponse response = expenseService.createExpense(request);

        return ResponseEntity.created(URI.create("api/expense" + response.id())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> findExpense(@PathVariable Long id) {
        ExpenseResponse response = expenseService.findExpenseByExpenseId(id);

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/house/{id}")
    public ResponseEntity<Page<ExpenseResponse>> findExpensesByHouse(
            @PathVariable
            Long id,
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC)
            Pageable pageable) {

            Page<ExpenseResponse> responses = expenseService.findExpensesByHouseId(id, pageable);

        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/participant/{id}")
    public ResponseEntity<Boolean> settledByExpenseParticipantId(
            @PathVariable Long id,
            @RequestParam Boolean isSettled) {
        Boolean response = expenseService.settledByExpenseParticipantId(id, isSettled);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateExpenseDetails(
            @PathVariable Long id,
            @RequestBody ExpenseRequest request) {

        expenseService.updateExpenseDetailsById(id, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpenseByExpenseId(id);

        return ResponseEntity.noContent().build();
    }
}

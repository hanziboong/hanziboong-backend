package skhu.hanziboong.ledger.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<Void> createExpense(ExpenseRequest request) {
        ExpenseResponse response = expenseService.createExpense(request);

        return ResponseEntity.created(URI.create("api/expense" + response.id())).build();
    }
}

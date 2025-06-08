package skhu.hanziboong.ledger.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import skhu.hanziboong.ledger.domain.Expense;
import skhu.hanziboong.ledger.domain.ExpenseParticipant;
import skhu.hanziboong.member.domain.Member;

@Builder
public record ExpenseResponse(
        Long id,
        String title,
        Long expenditure,
        String memo,
        LocalDateTime spendAt,
        Member paidBy,
        List<ExpenseParticipant> expenseParticipants
) {
    public static ExpenseResponse from(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .expenditure(expense.getExpenditure())
                .memo(expense.getMemo())
                .spendAt(expense.getSpendAt())
                .paidBy(expense.getPaidBy())
                .expenseParticipants(expense.getParticipants())
                .build();
    }
}

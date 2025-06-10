package skhu.hanziboong.expense.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import skhu.hanziboong.expense.domain.Expense;

@Builder
public record ExpenseResponse(
        Long id,
        String title,
        Long expenditure,
        String memo,
        LocalDateTime spendAt,
        PaidMemberDto paidMember,
        List<ExpenseParticipantDto> expenseParticipants
) {
    public static ExpenseResponse from(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .expenditure(expense.getExpenditure())
                .memo(expense.getMemo())
                .spendAt(expense.getCreatedAt())
                .paidMember(PaidMemberDto.from(expense.getPaidBy()))
                .expenseParticipants(ExpenseParticipantDto.from(expense))
                .build();
    }
}

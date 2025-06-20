package skhu.hanziboong.expense.dto.response;

import lombok.Builder;
import skhu.hanziboong.expense.domain.Expense;

@Builder
public record ExpenseIdResponse(
        Long id
) {

    public static ExpenseIdResponse from(Expense expense) {
        return ExpenseIdResponse.builder()
                .id(expense.getId())
                .build();
    }
}

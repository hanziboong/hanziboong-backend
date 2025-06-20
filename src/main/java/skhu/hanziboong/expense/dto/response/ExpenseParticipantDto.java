package skhu.hanziboong.expense.dto.response;

import java.util.List;
import lombok.Builder;
import skhu.hanziboong.expense.domain.Expense;
import skhu.hanziboong.expense.domain.ExpenseParticipant;

@Builder
public record ExpenseParticipantDto(
        Long id,
        Long memberId,
        String nickName,
        Boolean settled,
        Long amountToPay
) {
    public static List<ExpenseParticipantDto> from(List<ExpenseParticipant> expenseParticipant) {
        return expenseParticipant.stream()
                .map(ExpenseParticipantDto::of)
                .toList();
    }

    private static ExpenseParticipantDto of(ExpenseParticipant expenseParticipant) {
        return ExpenseParticipantDto.builder()
                .id(expenseParticipant.getId())
                .memberId(expenseParticipant.getParticipantMember().getId())
                .nickName(expenseParticipant.getParticipantMember().getNickname())
                .settled(expenseParticipant.getSettled())
                .amountToPay(expenseParticipant.getAmountToPay())
                .build();
    }
}

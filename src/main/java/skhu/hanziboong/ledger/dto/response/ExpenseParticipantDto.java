package skhu.hanziboong.ledger.dto.response;

import java.util.List;
import lombok.Builder;
import skhu.hanziboong.ledger.domain.Expense;
import skhu.hanziboong.ledger.domain.ExpenseParticipant;

@Builder
public record ExpenseParticipantDto(
        Long participantMemberId,
        String nickName,
        Boolean settled
) {
    public static List<ExpenseParticipantDto> from(Expense expense) {
        return expense.getParticipants().stream()
                .map(ExpenseParticipantDto::of)
                .toList();
    }

    private static ExpenseParticipantDto of(ExpenseParticipant expenseParticipant) {
        return ExpenseParticipantDto.builder()
                .participantMemberId(expenseParticipant.getId())
                .nickName(expenseParticipant.getParticipantMember().getNickname())
                .settled(expenseParticipant.getSettled())
                .build();
    }
}

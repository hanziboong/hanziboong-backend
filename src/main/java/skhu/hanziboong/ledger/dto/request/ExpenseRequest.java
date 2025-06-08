package skhu.hanziboong.ledger.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.ledger.domain.Expense;
import skhu.hanziboong.ledger.domain.ExpenseParticipant;
import skhu.hanziboong.member.domain.Member;

public record ExpenseRequest(
        Long houseId,
        Long paidMemberId,
        List<Long> participantMemberId,
        String title,
        Long expenditure,
        String memo,
        LocalDateTime spendAt
) {

    public Expense toExpense(Member paidBy, House house) {
        return new Expense(title, expenditure, spendAt, memo, house, paidBy);
    }
}

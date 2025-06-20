package skhu.hanziboong.expense.dto.request;

import java.util.List;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.expense.domain.Expense;
import skhu.hanziboong.member.domain.Member;

public record ExpenseRequest(
        Long houseId,
        Long paidMemberId,
        List<Long> participantMemberId,
        String title,
        Long expenditure,
        String memo
) {

    public Expense toExpense(Member paidBy, House house) {
        return new Expense(title, expenditure, memo, house, paidBy);
    }
}

package skhu.hanziboong.expense.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import skhu.hanziboong.global.BaseEntity;
import skhu.hanziboong.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpenseParticipant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean settled;

    @Column(nullable = false)
    private Long amountToPay;

    @Column(nullable = false)
    private Long settledAmount;

    // 필요하다면 양방향 설계로 -> 한 명의 멤버가 본인이 참여한 지출 내역을 조회할 수 있도록
    @ManyToOne(fetch = FetchType.LAZY)
    private Member participantMember;

    @ManyToOne(fetch = FetchType.LAZY)
    private Expense expense;

    private ExpenseParticipant(Long amountToPay, Member participantMember) {
        validatePaidAmountNotNegative(amountToPay);
        validatePaidMemberNotNull(participantMember);
        this.settled = false;
        this.amountToPay = amountToPay;
        this.settledAmount = 0L;
        this.participantMember = participantMember;
    }

    public static ExpenseParticipant of(Member participantMember, Long amountToPay, Expense expense) {
        ExpenseParticipant expenseParticipant = new ExpenseParticipant(amountToPay, participantMember);
        expenseParticipant.expense = expense;

        return expenseParticipant;
    }

    public void settled() {
        this.settledAmount = this.amountToPay;
        this.settled = true;
    }

    public void unSettled() {
        this.settledAmount = 0L;
        this.settled = false;
    }

    private void validatePaidAmountNotNegative(Long amountToPay) {
        if (amountToPay == null || amountToPay < 0) {
            throw new IllegalArgumentException("정산할 금액은 null이거나 음수일 수 없습니다.");
        }
    }

    private void validatePaidMemberNotNull(Member participantMember) {
        if (participantMember == null) {
            throw new IllegalArgumentException("정산 참여자는 null일 수 없습니다.");
        }
    }
}

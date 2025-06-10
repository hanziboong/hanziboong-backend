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

    /** 기존에는 Expense -> ExpenseParticipant 단방향 구조였지만 조회, 수정, 삭제, 정렬, 통계 집계등
     다양한 쿼리 대상이 될 것이여서 양방향으로 설계했습니다.
     하지만 현재 참여한 인원의 정산 상태를 위한 테이블이 Expense를 알아야할까?에 대한 의문이 들었습니다.
     외래키 관리, 영속성 전이의 일관성에 대한 주의, insert보다 update쿼리를 더 많이 발생 시킴 (컬렉션 교체시에 전체 delete후에 insert로 동작)
     이러한 이유들로 인해서 도메인 요구사항에 대한 열림(?) vs 유지보수와 성능 이 2가지로 고민했습니다
     결국 유지보수와 성능을 선택해서 양방향으로 설계를 수정했습니다.
     이 부분 어떻게 생각하시는지 궁금합니다ㅠ
     **/
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

package skhu.hanziboong.ledger.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import skhu.hanziboong.global.BaseEntity;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expense extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long expenditure;

    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    private House house;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member paidBy;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExpenseParticipant> participants = new ArrayList<>();

    @Builder
    public Expense(String title, Long expenditure, String memo,
                   House house, Member paidBy) {
        validateNotBlank(title);
        validateExpenditureIsNegative(expenditure);
        validateNotNull(house, paidBy);
        this.title = title;
        this.expenditure = expenditure;
        this.memo = memo;
        this.house = house;
        this.paidBy = paidBy;
    }

    public void update(String title, Long expenditure, String memo) {
        validateNotBlank(title);
        validateExpenditureIsNegative(expenditure);
        this.title = title;
        this.expenditure = expenditure;
        this.memo = memo;
    }

    public void addParticipants(List<Member> expenseParticipants) {
        Long perMemberAmount = calculateSettleAmount(expenseParticipants);

        for (Member expenseParticipant : expenseParticipants) {
            ExpenseParticipant participant = ExpenseParticipant.of(expenseParticipant, perMemberAmount, this);
            this.participants.add(participant);
        }
    }

    // 정산시에 소수점에 대한 부분은 아직 고려하지 않았습니다. 부동소수점 연산으로 오차 없이 할 수 있을 것 같은데 추후에 회의해보고 도입해보겠습니다.
    private Long calculateSettleAmount(List<Member> expenseParticipants) {
        return this.expenditure / expenseParticipants.size();
    }

    private void validateNotBlank(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("공동 지출 내역의 제목은 비어있을 수 없습니다.");
        }
    }

    private void validateExpenditureIsNegative(Long expenditure) {
        if (expenditure < 0) {
            throw new IllegalArgumentException("공동 지출 금액이 음수 일 수 없습니다.");
        }
    }

    private void validateNotNull(House house, Member paidBy) {
        if (house == null) {
            throw new IllegalArgumentException("주거지 정보는 null일 수 없습니다.");
        }

        if (paidBy == null) {
            throw new IllegalArgumentException("공동지출내역 지불자는 null일 수 없습니다.");
        }
    }
}

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
        this.title = title;
        this.expenditure = expenditure;
        this.memo = memo;
        this.house = house;
        this.paidBy = paidBy;
    }

    public void addParticipants(List<Member> expenseParticipants) {
        for (Member expenseParticipant : expenseParticipants) {
            addParticipant(expenseParticipant);
        }
    }

    private void addParticipant(Member expenseParticipant) {
        ExpenseParticipant participant = ExpenseParticipant.of(expenseParticipant, this);
        this.participants.add(participant);
    }
}

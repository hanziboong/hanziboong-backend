package skhu.hanziboong.ledger.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import skhu.hanziboong.member.domain.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpenseParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean settled;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member participantMember;

    @Builder
    public ExpenseParticipant(Boolean settled, Member participantMember) {
        this.settled = settled;
        this.participantMember = participantMember;
    }

    public void settled() {
        this.settled = true;
    }
}

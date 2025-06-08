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

    // 필요하다면 양방향 설계로 -> 한 명의 멤버가 본인이 참여한 지출 내역을 조회할 수 있도록
    @ManyToOne(fetch = FetchType.LAZY)
    private Member participantMember;

    @Builder
    private ExpenseParticipant(Boolean settled, Member participantMember) {
        this.settled = settled;
        this.participantMember = participantMember;
    }

    public static ExpenseParticipant of(Member participantMember) {
        return new ExpenseParticipant(false, participantMember);
    }

    public void settled() {
        this.settled = true;
    }
}

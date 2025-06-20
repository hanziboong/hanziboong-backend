package skhu.hanziboong.member.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import skhu.hanziboong.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByIdInAndHouse_Id(List<Long> participantMemberId, Long houseId);
}

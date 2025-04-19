package skhu.hanziboong.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skhu.hanziboong.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}

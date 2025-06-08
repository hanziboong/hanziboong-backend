package skhu.hanziboong.ledger.dto.response;

import lombok.Builder;
import skhu.hanziboong.member.domain.Member;

@Builder
public record PaidMemberDto(
        Long id,
        String nickName
) {

    public static PaidMemberDto from(Member member) {
        return PaidMemberDto.builder()
                .id(member.getId())
                .nickName(member.getNickname())
                .build();
    }
}

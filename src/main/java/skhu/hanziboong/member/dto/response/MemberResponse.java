package skhu.hanziboong.member.dto.response;

import lombok.Builder;
import skhu.hanziboong.member.domain.Member;

@Builder
public record MemberResponse(
        Long id,
        String nickname,
        Long houseId
) {
    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .houseId(member.getHouseId())
                .build();
    }
}

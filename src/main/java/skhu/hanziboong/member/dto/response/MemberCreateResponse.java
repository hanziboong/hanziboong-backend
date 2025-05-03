package skhu.hanziboong.member.dto.response;

import skhu.hanziboong.member.domain.Member;

public record MemberCreateResponse(
        Long id
) {
    public static MemberCreateResponse from(Member member) {
        return new MemberCreateResponse(member.getId());
    }
}

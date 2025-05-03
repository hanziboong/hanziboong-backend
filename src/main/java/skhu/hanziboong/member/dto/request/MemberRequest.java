package skhu.hanziboong.member.dto.request;

import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.member.domain.Member;

public record MemberRequest(
        String username,
        String nickname,
        Long houseId
) {
    public Member toMember(House house) {
        return new Member(username, nickname, house);
    }
}

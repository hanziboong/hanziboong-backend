package skhu.hanziboong.member.fixture;

import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.member.domain.Member;

public enum MemberFixture {
    MEMBER("testusername", "testnickname");

    private final String username;
    private final String nickname;

    MemberFixture(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public Member createMemberByHouse(House house) {
        return new Member(username, nickname, house);
    }
}

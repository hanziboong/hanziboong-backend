package skhu.hanziboong.member.fixture;

import static skhu.hanziboong.house.fixture.HouseFixture.DORMITORY;

import java.util.function.BiFunction;
import skhu.hanziboong.member.domain.Member;

public enum MemberFixture {
    MEMBER((username, nickname) -> new Member(username, nickname, DORMITORY.create()));

    private final BiFunction<String, String, Member> generator;

    MemberFixture(BiFunction<String, String, Member> generator) {
        this.generator = generator;
    }

    public Member create() {
        return generator.apply("testusername", "testnickname");
    }
}

package skhu.hanziboong.rule.dto.request;

import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.house.domain.HouseType;
import skhu.hanziboong.member.domain.Member;
import skhu.hanziboong.rule.domain.Rule;

public record RuleRequest(
        String title,
        String description
) {
    public Rule toRule() {
        // TODO: house와 member는 임시 객체이므로 기능 구현 후 대체해야 함.
        House house = new House(HouseType.MONTHLY_RENT);
        Member member = new Member("username", "nickname", house);
        return new Rule(title, description, member);
    }
}

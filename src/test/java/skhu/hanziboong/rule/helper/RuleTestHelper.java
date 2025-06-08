package skhu.hanziboong.rule.helper;

import static skhu.hanziboong.house.fixture.HouseFixture.DORMITORY;
import static skhu.hanziboong.member.fixture.MemberFixture.MEMBER;
import static skhu.hanziboong.rule.fixture.RuleFixture.RULE;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.house.repository.HouseRepository;
import skhu.hanziboong.member.domain.Member;
import skhu.hanziboong.member.repository.MemberRepository;
import skhu.hanziboong.rule.domain.Rule;
import skhu.hanziboong.rule.repository.RuleRepository;

@Component
public class RuleTestHelper {

    private final RuleRepository ruleRepository;
    private final MemberRepository memberRepository;
    private final HouseRepository houseRepository;

    public RuleTestHelper(
            RuleRepository ruleRepository,
            MemberRepository memberRepository,
            HouseRepository houseRepository
    ) {
        this.ruleRepository = ruleRepository;
        this.memberRepository = memberRepository;
        this.houseRepository = houseRepository;
    }

    @Transactional
    public Rule initRuleTestData() {
        House house = persistHouse();
        Member author = persistMember(house);
        Rule rule = RULE.createRuleByMember(author);

        return ruleRepository.save(rule);
    }

    private House persistHouse() {
        House house = DORMITORY.create();
        return houseRepository.save(house);
    }

    private Member persistMember(House house) {
        Member author = MEMBER.createMemberByHouse(house);
        return memberRepository.save(author);
    }

    @Transactional
    public Rule initRuleTestDataByHouse(House house) {
        Member author = persistMember(house);
        Rule rule = RULE.createRuleByMember(author);

        return ruleRepository.save(rule);
    }

    @Transactional
    public Rule initRuleTestDataByAuthor(Member author) {
        Rule rule = RULE.createRuleByMember(author);

        return ruleRepository.save(rule);
    }
}

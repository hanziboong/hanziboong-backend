package skhu.hanziboong.rule.fixture;

import skhu.hanziboong.member.domain.Member;
import skhu.hanziboong.rule.domain.Rule;

public enum RuleFixture {
    RULE("test rule title", "test rule description");

    private final String title;
    private final String description;

    RuleFixture(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Rule createRuleByMember(Member member) {
        return new Rule(title, description, member);
    }
}

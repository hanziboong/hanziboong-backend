package skhu.hanziboong.rule.fixture;

import static skhu.hanziboong.member.fixture.MemberFixture.MEMBER;

import java.util.function.BiFunction;
import skhu.hanziboong.rule.domain.Rule;

public enum RuleFixture {
    RULE((title, description) -> new Rule(title, description, MEMBER.create()));

    private final BiFunction<String, String, Rule> generator;

    RuleFixture(BiFunction<String, String, Rule> generator) {
        this.generator = generator;
    }

    public Rule create() {
        return generator.apply("test rule title", "test rule description");
    }
}

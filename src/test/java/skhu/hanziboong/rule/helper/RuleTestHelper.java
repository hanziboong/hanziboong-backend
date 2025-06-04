package skhu.hanziboong.rule.helper;

import org.springframework.stereotype.Component;
import skhu.hanziboong.rule.domain.Rule;
import skhu.hanziboong.rule.fixture.RuleFixture;
import skhu.hanziboong.rule.repository.RuleRepository;

@Component
public class RuleTestHelper {

    private final RuleRepository ruleRepository;

    public RuleTestHelper(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public void initRuleTestData() {
        Rule rule = RuleFixture.RULE.create();
        ruleRepository.save(rule);
    }
}

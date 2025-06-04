package skhu.hanziboong.rule.service;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import skhu.hanziboong.member.helper.MemberTestHelper;
import skhu.hanziboong.rule.dto.request.RuleRequest;

@Import({RuleService.class, MemberTestHelper.class})
@DataJpaTest
class RuleServiceTest {

    private final RuleService ruleService;
    private final MemberTestHelper memberTestHelper;

    @Autowired
    public RuleServiceTest(RuleService ruleService, MemberTestHelper memberTestHelper) {
        this.ruleService = ruleService;
        this.memberTestHelper = memberTestHelper;
    }

    @DisplayName("규칙을 생성할 수 있다.")
    @Test
    void createRule() {
        memberTestHelper.initMemberTestData();
        RuleRequest request = new RuleRequest("test title", "test description", 1L);

        assertThatCode(() -> ruleService.createRule(request)).doesNotThrowAnyException();
    }

    @DisplayName("ID를 기준으로 규칙을 조회할 수 있다.")
    @Test
    void findRuleById() {
    }

    @DisplayName("존재하지 않는 ID를 기준으로 규칙을 조회하면 예외가 발생한다.")
    @Test
    void findRuleByNotExistId() {

    }

    @DisplayName("집 ID를 기준으로 규칙 목록을 조회할 수 있다.")
    @Test
    void findRulesByHouseId() {
    }

    @DisplayName("ID를 기준으로 규칙을 수정할 수 있다.")
    @Test
    void updateRuleById() {
    }

    @DisplayName("ID를 기준으로 규칙을 삭제할 수 있다.")
    @Test
    void deleteRuleById() {
    }
}
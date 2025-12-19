package skhu.hanziboong.rule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import skhu.hanziboong.global.ServiceTest;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.house.helper.HouseTestHelper;
import skhu.hanziboong.member.domain.Member;
import skhu.hanziboong.member.helper.MemberTestHelper;
import skhu.hanziboong.rule.domain.Rule;
import skhu.hanziboong.rule.dto.request.RuleRequest;
import skhu.hanziboong.rule.dto.response.RuleResponse;
import skhu.hanziboong.rule.helper.RuleTestHelper;

@DisplayName("규칙 서비스 계층 테스트")
@ServiceTest
class RuleServiceTest {

    private final RuleService ruleService;
    private final MemberTestHelper memberTestHelper;
    private final RuleTestHelper ruleTestHelper;
    private final HouseTestHelper houseTestHelper;

    public RuleServiceTest(
            RuleService ruleService,
            MemberTestHelper memberTestHelper,
            RuleTestHelper ruleTestHelper,
            HouseTestHelper houseTestHelper
    ) {
        this.ruleService = ruleService;
        this.memberTestHelper = memberTestHelper;
        this.ruleTestHelper = ruleTestHelper;
        this.houseTestHelper = houseTestHelper;
    }

    @DisplayName("규칙을 생성할 수 있다.")
    @Test
    void createRule() {
        Member savedMember = memberTestHelper.initMemberTestData();
        RuleRequest request = new RuleRequest("test title", "test description", savedMember.getId());

        assertThatCode(() -> ruleService.createRule(request)).doesNotThrowAnyException();
    }

    @DisplayName("ID를 기준으로 규칙을 조회할 수 있다.")
    @Test
    void findRuleById() {
        Rule savedRule = ruleTestHelper.initRuleTestData();

        assertThatCode(() -> ruleService.findRuleById(savedRule.getId())).doesNotThrowAnyException();
    }

    @DisplayName("존재하지 않는 ID를 기준으로 규칙을 조회하면 예외가 발생한다.")
    @Test
    void findRuleByNotExistId() {
        assertThatThrownBy(() -> ruleService.findRuleById(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 규칙입니다.");
    }

    @DisplayName("집 ID를 기준으로 규칙 목록을 조회할 수 있다.")
    @Test
    void findRulesByHouseId() {
        House savedHouse = houseTestHelper.initHouseData();
        ruleTestHelper.initRuleTestDataByHouse(savedHouse);

        assertThatCode(() -> ruleService.findRulesByHouseId(savedHouse.getId(), Pageable.ofSize(5)))
                .doesNotThrowAnyException();
    }

    @DisplayName("ID를 기준으로 규칙을 수정할 수 있다.")
    @Test
    void updateRuleById() {
        Member savedMember = memberTestHelper.initMemberTestData();
        Rule originalRule = ruleTestHelper.initRuleTestDataByAuthor(savedMember);
        RuleRequest request = new RuleRequest("update title", "update description", savedMember.getId());

        ruleService.updateRuleById(originalRule.getId(), request);
        RuleResponse updateResponse = ruleService.findRuleById(originalRule.getId());

        assertAll(
                () -> assertThat(updateResponse.title()).isEqualTo(request.title()),
                () -> assertThat(updateResponse.description()).isEqualTo(request.description())
        );
    }

    @DisplayName("ID를 기준으로 규칙을 삭제할 수 있다.")
    @Test
    void deleteRuleById() {
        Rule savedRule = ruleTestHelper.initRuleTestData();

        assertThatCode(() -> ruleService.deleteRuleById(savedRule.getId())).doesNotThrowAnyException();
    }
}

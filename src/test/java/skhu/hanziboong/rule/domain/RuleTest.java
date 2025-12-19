package skhu.hanziboong.rule.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static skhu.hanziboong.house.fixture.HouseFixture.DORMITORY;
import static skhu.hanziboong.member.fixture.MemberFixture.MEMBER;
import static skhu.hanziboong.rule.fixture.RuleFixture.RULE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.member.domain.Member;

@DisplayName("규칙 도메인 테스트")
public class RuleTest {

    private static final House HOUSE = DORMITORY.create();
    private static final Member AUTHOR = MEMBER.createMemberByHouse(HOUSE);

    @DisplayName("제목, 설명, 작성자가 주어지면 규칙을 생성할 수 있다.")
    @Test
    void createRule() {
        String title = "test title";
        String description = "test description";

        assertThatCode(() -> new Rule(title, description, AUTHOR)).doesNotThrowAnyException();
    }

    @DisplayName("제목과 설명은 수정할 수 있다.")
    @Test
    void updateRule() {
        Rule rule = RULE.createRuleByMember(AUTHOR);
        String updatedTitle = "updated title";
        String updatedDescription = "updated description";

        rule.update(updatedTitle, updatedDescription);

        assertAll(
                () -> assertThat(rule.getTitle()).isEqualTo(updatedTitle),
                () -> assertThat(rule.getDescription()).isEqualTo(updatedDescription)
        );
    }

    @DisplayName("제목은 비어있을 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void validRuleTitle(String title) {
        String description = "test description";

        assertThatThrownBy(() -> new Rule(title, description, AUTHOR))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("규칙 제목은 비어있을 수 없습니다.");
    }
}

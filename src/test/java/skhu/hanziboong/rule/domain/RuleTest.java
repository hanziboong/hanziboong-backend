package skhu.hanziboong.rule.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static skhu.hanziboong.member.fixture.MemberFixture.MEMBER;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import skhu.hanziboong.member.domain.Member;
import skhu.hanziboong.rule.fixture.RuleFixture;

@DisplayName("규칙 테스트")
public class RuleTest {

    @DisplayName("제목, 설명, 작성자가 주어지면 규칙을 생성할 수 있다.")
    @Test
    void createRule() {
        String title = "test title";
        String description = "test description";
        Member author = MEMBER.create();

        assertThatCode(() -> new Rule(title, description, author)).doesNotThrowAnyException();
    }

    @DisplayName("제목과 설명은 수정할 수 있다.")
    @Test
    void updateRule() {
        String title = "updated title";
        String description = "updated description";
        Rule rule = RuleFixture.RULE.create();

        rule.update(title, description);

        assertAll(
                () -> assertThat(rule.getTitle()).isEqualTo(title),
                () -> assertThat(rule.getDescription()).isEqualTo(description)
        );
    }

    @DisplayName("제목은 비어있을 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    void validRuleTitle(String title) {
        String description = "test description";
        Member author = MEMBER.create();

        assertThatThrownBy(() -> new Rule(title, description, author))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("규칙 제목은 비어있을 수 없습니다.");
    }
}

package skhu.hanziboong.rule.dto.request;

import skhu.hanziboong.member.domain.Member;
import skhu.hanziboong.rule.domain.Rule;

public record RuleRequest(
        String title,
        String description,
        Long memberId
) {
    public Rule toRule(Member member) {
        return new Rule(title, description, member);
    }
}

package skhu.hanziboong.rule.dto.response;

import skhu.hanziboong.rule.domain.Rule;

public record RuleCreateResponse(
        Long id
) {
    public static RuleCreateResponse from(Rule rule) {
        return new RuleCreateResponse(rule.getId());
    }
}

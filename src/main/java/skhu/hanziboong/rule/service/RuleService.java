package skhu.hanziboong.rule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.house.repository.HouseRepository;
import skhu.hanziboong.member.domain.Member;
import skhu.hanziboong.member.repository.MemberRepository;
import skhu.hanziboong.rule.domain.Rule;
import skhu.hanziboong.rule.dto.request.RuleRequest;
import skhu.hanziboong.rule.dto.response.RuleCreateResponse;
import skhu.hanziboong.rule.dto.response.RuleResponse;
import skhu.hanziboong.rule.repository.RuleRepository;

@RequiredArgsConstructor
@Service
public class RuleService {

    private final RuleRepository ruleRepository;
    private final MemberRepository memberRepository;
    private final HouseRepository houseRepository;

    @Transactional
    public RuleCreateResponse createRule(RuleRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Rule rule = ruleRepository.save(request.toRule(member));

        return RuleCreateResponse.from(rule);
    }

    @Transactional(readOnly = true)
    public RuleResponse findRuleById(Long id) {
        Rule rule = ruleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 규칙입니다."));

        return RuleResponse.from(rule);
    }

    @Transactional(readOnly = true)
    public Page<RuleResponse> findRulesByHouseId(Long id, Pageable pageable) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 집입니다."));

        Page<Rule> rules = ruleRepository.findAllByHouse(house, pageable);
        return rules.map(RuleResponse::from);
    }

    @Transactional
    public void updateRuleById(Long id, RuleRequest request) {
        Rule rule = ruleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 규칙입니다."));

        rule.update(request.title(), request.description());
    }

    @Transactional
    public void deleteRuleById(Long id) {
        ruleRepository.deleteById(id);
    }
}

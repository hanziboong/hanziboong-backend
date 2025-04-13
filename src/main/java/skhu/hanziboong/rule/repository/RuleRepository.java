package skhu.hanziboong.rule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skhu.hanziboong.rule.domain.Rule;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
}

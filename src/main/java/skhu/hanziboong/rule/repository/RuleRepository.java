package skhu.hanziboong.rule.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.rule.domain.Rule;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

    Page<Rule> findAllByHouse(House house, Pageable pageable);
}

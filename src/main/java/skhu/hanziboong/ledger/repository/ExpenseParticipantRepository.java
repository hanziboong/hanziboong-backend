package skhu.hanziboong.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skhu.hanziboong.ledger.domain.ExpenseParticipant;

public interface ExpenseParticipantRepository extends JpaRepository<ExpenseParticipant, Long> {
}

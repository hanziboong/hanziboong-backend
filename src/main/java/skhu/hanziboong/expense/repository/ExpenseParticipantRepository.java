package skhu.hanziboong.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skhu.hanziboong.expense.domain.ExpenseParticipant;

public interface ExpenseParticipantRepository extends JpaRepository<ExpenseParticipant, Long> {
}

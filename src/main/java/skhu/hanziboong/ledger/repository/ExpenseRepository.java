package skhu.hanziboong.ledger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skhu.hanziboong.ledger.domain.Expense;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}

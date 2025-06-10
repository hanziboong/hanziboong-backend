package skhu.hanziboong.expense.service;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.hanziboong.global.exception.CustomException;
import skhu.hanziboong.global.exception.ErrorCode;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.house.repository.HouseRepository;
import skhu.hanziboong.expense.domain.Expense;
import skhu.hanziboong.expense.domain.ExpenseParticipant;
import skhu.hanziboong.expense.dto.request.ExpenseRequest;
import skhu.hanziboong.expense.dto.response.ExpenseResponse;
import skhu.hanziboong.expense.repository.ExpenseParticipantRepository;
import skhu.hanziboong.expense.repository.ExpenseRepository;
import skhu.hanziboong.member.domain.Member;
import skhu.hanziboong.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpenseService {

    private final ExpenseParticipantRepository expenseParticipantRepository;
    private final ExpenseRepository expenseRepository;
    private final MemberRepository memberRepository;
    private final HouseRepository houseRepository;

    @Transactional
    public ExpenseResponse createExpense(ExpenseRequest request) {
        Member paidBy = memberRepository.findById(request.paidMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION,
                        ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage()));
        House house = houseRepository.findById(request.houseId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_EXCEPTION,
                        ErrorCode.NOT_FOUND_HOUSE_EXCEPTION.getMessage()));

        List<Member> participants = memberRepository.findByIdInAndHouse_Id(
                request.participantMemberId(), paidBy.getHouseId());

        Expense expense = request.toExpense(paidBy, house);
        expense.addParticipants(participants);
        expenseRepository.save(expense);

         /** 현재 코드는 dto 순환 참조를 방지하기 위해서 dto를 분리하면서 N+1 문제가 발생하는 구조가 되었어요.
         해결 방법들로는 dto projection이랑 fetch join등등 여러개를 찾아봤는데 아직 어떻게 적용하면 좀 잘 적용할 수 있을지 고민입니다..
         **/
        return ExpenseResponse.from(expense);
    }

    @Transactional(readOnly = true)
    public ExpenseResponse findExpenseByExpenseId(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXPENSE_EXCEPTION,
                        ErrorCode.NOT_FOUND_EXPENSE_EXCEPTION.getMessage()));

        return ExpenseResponse.from(expense);
    }

    @Transactional(readOnly = true)
    public Page<ExpenseResponse> findExpensesByHouseId(Long id, Pageable pageable) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_EXCEPTION,
                        ErrorCode.NOT_FOUND_HOUSE_EXCEPTION.getMessage()));

        List<Expense> expenses = expenseRepository.findAllByHouseWithParticipants(house);

        int start = (int) pageable.getOffset();
        int end = (Math.min(start + pageable.getPageSize(), expenses.size()));

        List<ExpenseResponse> pageCount = expenses.subList(start, end).stream()
                .map(ExpenseResponse::from)
                .toList();

        return new PageImpl<>(pageCount, pageable, expenses.size());
    }

    @Transactional
    public boolean settledByExpenseParticipantId(Long id, Boolean isSettled) {
        ExpenseParticipant expenseParticipant = expenseParticipantRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXPENSE_PARTICIPANT_EXCEPTION,
                        ErrorCode.NOT_FOUND_EXPENSE_PARTICIPANT_EXCEPTION.getMessage()));

        if (isSettled) {
            expenseParticipant.settled();
            return true;
        }

        expenseParticipant.unSettled();
        return false;
    }

    @Transactional
    public void updateExpenseDetailsById(Long id, ExpenseRequest request) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXPENSE_EXCEPTION,
                        ErrorCode.NOT_FOUND_EXPENSE_EXCEPTION.getMessage()));

        expense.update(request.title(), request.expenditure(), request.memo());
    }

    @Transactional
    public void deleteExpenseByExpenseId(Long id) {
        expenseRepository.deleteById(id);
    }
}

package skhu.hanziboong.expense.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.hanziboong.expense.dto.response.ExpenseIdResponse;
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
    public ExpenseIdResponse createExpense(ExpenseRequest request) {
        Member paidBy = memberRepository.findById(request.paidMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION,
                        ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage()));
        House house = houseRepository.findById(request.houseId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_EXCEPTION,
                        ErrorCode.NOT_FOUND_HOUSE_EXCEPTION.getMessage()));

        List<Member> participants = memberRepository.findByIdInAndHouse_Id(
                request.participantMemberId(), paidBy.getHouseId());

        Expense expense = request.toExpense(paidBy, house);
        expenseRepository.save(expense);

        long perAmount = expense.calculateSettleAmount(participants.size());

        List<ExpenseParticipant> expenseParticipants = participants.stream()
                .map(member -> ExpenseParticipant.of(member, perAmount, expense))
                .toList();

        expenseParticipantRepository.saveAll(expenseParticipants);

        return ExpenseIdResponse.from(expense);
    }

    @Transactional(readOnly = true)
    public ExpenseResponse findExpenseByExpenseId(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXPENSE_EXCEPTION,
                        ErrorCode.NOT_FOUND_EXPENSE_EXCEPTION.getMessage()));

        List<ExpenseParticipant> participants = expenseParticipantRepository.findByExpenseId(id);

        return ExpenseResponse.from(expense, participants);
    }

    @Transactional(readOnly = true)
    public Page<ExpenseResponse> findExpensesByHouseId(Long id, Pageable pageable) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOUSE_EXCEPTION,
                        ErrorCode.NOT_FOUND_HOUSE_EXCEPTION.getMessage()));

        List<Expense> expenses = expenseRepository.findAllByHouse(house);

        int start = (int) pageable.getOffset();
        int end = (Math.min(start + pageable.getPageSize(), expenses.size()));
        List<Expense> pagedExpenses = expenses.subList(start, end);

        List<Long> expenseIds = pagedExpenses.stream()
                .map(Expense::getId)
                .toList();

        List<ExpenseParticipant> participants = expenseParticipantRepository.findByExpenseIds(expenseIds);

        Map<Long, List<ExpenseParticipant>> participantMap = participants.stream()
                .collect(Collectors.groupingBy(expenseParticipant -> expenseParticipant.getExpense().getId()));

        List<ExpenseResponse> pageContent = pagedExpenses.stream()
                .map(expense -> ExpenseResponse.from(
                        expense,
                        participantMap.getOrDefault(expense.getId(), List.of())
                ))
                .toList();

        return new PageImpl<>(pageContent, pageable, expenses.size());
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

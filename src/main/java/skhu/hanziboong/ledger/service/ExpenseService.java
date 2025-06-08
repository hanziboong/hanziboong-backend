package skhu.hanziboong.ledger.service;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.hanziboong.global.exception.CustomException;
import skhu.hanziboong.global.exception.ErrorCode;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.house.repository.HouseRepository;
import skhu.hanziboong.ledger.domain.Expense;
import skhu.hanziboong.ledger.dto.request.ExpenseRequest;
import skhu.hanziboong.ledger.dto.response.ExpenseResponse;
import skhu.hanziboong.ledger.repository.ExpenseRepository;
import skhu.hanziboong.member.domain.Member;
import skhu.hanziboong.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpenseService {

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

        Expense expense = expenseRepository.save(request.toExpense(paidBy, house));
        expense.addParticipants(participants);

        return ExpenseResponse.from(expense);
    }

//    @Transactional(readOnly = true)
//    public Page<ExpenseResponse> findExpensesByHouseId(Long id, Pageable pageable) {
//        Page<Expense> expense = expenseRepository.
//    }
}

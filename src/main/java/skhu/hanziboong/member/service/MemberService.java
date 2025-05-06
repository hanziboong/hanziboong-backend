package skhu.hanziboong.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.house.repository.HouseRepository;
import skhu.hanziboong.member.domain.Member;
import skhu.hanziboong.member.dto.request.MemberRequest;
import skhu.hanziboong.member.dto.response.MemberCreateResponse;
import skhu.hanziboong.member.dto.response.MemberResponse;
import skhu.hanziboong.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final HouseRepository houseRepository;

    @Transactional
    public MemberCreateResponse createMember(MemberRequest request) {
        House house = houseRepository.findById(request.houseId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 집입니다."));
        Member member = memberRepository.save(request.toMember(house));

        return MemberCreateResponse.from(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse findMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return MemberResponse.from(member);
    }
}

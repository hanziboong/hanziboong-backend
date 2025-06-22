package skhu.hanziboong.house.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.house.dto.request.HouseRequest;
import skhu.hanziboong.house.dto.response.HouseCreateResponse;
import skhu.hanziboong.house.repository.HouseRepository;
import skhu.hanziboong.member.dto.response.MemberResponse;
import skhu.hanziboong.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class HouseService {

    private final HouseRepository houseRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public HouseCreateResponse createHouse(HouseRequest request) {
        House house = houseRepository.save(request.toHouse());

        return HouseCreateResponse.from(house);
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findMembersById(Long id) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 집입니다."));

        return memberRepository.findAllByHouse(house)
                .stream()
                .map(MemberResponse::from)
                .toList();
    }
}

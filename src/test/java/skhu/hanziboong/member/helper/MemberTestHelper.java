package skhu.hanziboong.member.helper;

import static skhu.hanziboong.house.fixture.HouseFixture.DORMITORY;
import static skhu.hanziboong.member.fixture.MemberFixture.MEMBER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import skhu.hanziboong.house.domain.House;
import skhu.hanziboong.house.repository.HouseRepository;
import skhu.hanziboong.member.domain.Member;
import skhu.hanziboong.member.repository.MemberRepository;

@Component
public class MemberTestHelper {

    private final MemberRepository memberRepository;
    private final HouseRepository houseRepository;

    @Autowired
    public MemberTestHelper(MemberRepository memberRepository, HouseRepository houseRepository) {
        this.memberRepository = memberRepository;
        this.houseRepository = houseRepository;
    }

    @Transactional
    public void initMemberTestData() {
        House house = persistHouse();
        Member member = MEMBER.createWith(house);
        memberRepository.save(member);
    }

    private House persistHouse() {
        House house = DORMITORY.create();
        houseRepository.save(house);
        return house;
    }
}

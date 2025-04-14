package skhu.hanziboong.house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skhu.hanziboong.house.domain.House;

public interface HouseRepository extends JpaRepository<House, Long> {
}

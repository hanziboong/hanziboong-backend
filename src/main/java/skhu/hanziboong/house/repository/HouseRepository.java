package skhu.hanziboong.house.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import skhu.hanziboong.house.domain.House;

public interface HouseRepository extends JpaRepository<House, Long> {


}

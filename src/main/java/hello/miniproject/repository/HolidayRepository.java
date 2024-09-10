package hello.miniproject.repository;

import hello.miniproject.entity.HolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<HolidayEntity, Integer> {




}

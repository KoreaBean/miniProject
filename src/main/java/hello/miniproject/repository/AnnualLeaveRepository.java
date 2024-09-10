package hello.miniproject.repository;

import hello.miniproject.entity.AnnualLeaveEntity;
import hello.miniproject.entity.primarykey.AnnualLeavePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnnualLeaveRepository extends JpaRepository<AnnualLeaveEntity, AnnualLeavePk> {

    List<AnnualLeaveEntity> findByVacation(LocalDate date);

}

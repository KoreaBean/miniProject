package hello.miniproject.repository;

import hello.miniproject.entity.AnnualLeaveEntity;
import hello.miniproject.entity.primarykey.AnnualLeavePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnualLeaveRepository extends JpaRepository<AnnualLeaveEntity, AnnualLeavePk> {


}

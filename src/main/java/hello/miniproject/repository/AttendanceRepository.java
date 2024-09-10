package hello.miniproject.repository;

import hello.miniproject.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {

    List<AttendanceEntity> findByEmployeeIdAndDateStartingWith(Long employeeId, String targetDate);

    Boolean existsByEmployeeId(Long employeeId);
    boolean existsByEmployeeIdAndOutTimeIsNull(Long employeeId);
    //outTime이 비어있는 유저 필드 반환
    Optional<AttendanceEntity> findByEmployeeIdAndOutTimeIsNull(Long employeeId);
}

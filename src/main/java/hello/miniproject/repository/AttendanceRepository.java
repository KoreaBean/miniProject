package hello.miniproject.repository;

import hello.miniproject.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {

    List<AttendanceEntity> findByEmployeeIdAndEnterTimeStartingWith(Long id, String date);

    boolean existsByEmployeeIdAndOutTimeIsNull(Long employeeId);
    //outTime이 비어있는 유저 필드 반환
    Optional<AttendanceEntity> findByEmployeeIdAndOutTimeIsNull(Long employeeId);
}

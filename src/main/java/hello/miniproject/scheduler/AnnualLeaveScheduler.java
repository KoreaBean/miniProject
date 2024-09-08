package hello.miniproject.scheduler;

import hello.miniproject.entity.EmployeeEntity;
import hello.miniproject.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Slf4j
@Component
@RequiredArgsConstructor
public class AnnualLeaveScheduler {

    private final EmployeeRepository employeeRepository;

    @Scheduled(cron = "0 0 0 1 1 *") // 매년 1월 1일 자정 실행
    @Transactional
    public void resetAnnualLeaves(){
        List<EmployeeEntity> all = employeeRepository.findAll();
        for (EmployeeEntity employee : all) {
            initAnnualLeave(employee);


        }
    }

    private static void initAnnualLeave(EmployeeEntity employee) {
        // 해당 년도
        int currentYear = LocalDate.now().getYear();
        log.info("scheduler currentYear={}",currentYear);
        // 입사 년도
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime targetYear = LocalDateTime.parse(employee.getWorkStartDate(), formatter);
        int year = targetYear.getYear();
        log.info("scheduler targetYear={}",year);

        if (currentYear == year){
            employee.setAnnualLeaves(11);
        }else {
            employee.setAnnualLeaves(15);
        }


    }


}

package hello.miniproject.scheduler;

import hello.miniproject.dto.response.GetHolidayResponseDto;
import hello.miniproject.entity.AnnualLeaveEntity;
import hello.miniproject.entity.AttendanceEntity;
import hello.miniproject.entity.EmployeeEntity;
import hello.miniproject.repository.AnnualLeaveRepository;
import hello.miniproject.repository.AttendanceRepository;
import hello.miniproject.repository.EmployeeRepository;
import hello.miniproject.repository.HolidayRepository;
import hello.miniproject.service.TeamService;
import hello.miniproject.service.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    private final AnnualLeaveRepository annualLeaveRepository;
    private final AttendanceRepository attendanceRepository;
    private final HolidayRepository holidayRepository;
    private final WebClientService webClientService;
    @Scheduled(cron = "0 0 0 1 1 *") // 매년 1월 1일 자정 실행
    @Transactional
    public void resetAnnualLeaves(){
        List<EmployeeEntity> all = employeeRepository.findAll();
        for (EmployeeEntity employee : all) {
            initAnnualLeave(employee);


        }
    }
    // 매일 자정에 휴가 인원 출퇴근 기록부에 저장
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void attendanceManagement(){
        LocalDate now = LocalDate.now();
        log.info("scheduler ={}",now);
        List<AnnualLeaveEntity> targetMember = annualLeaveRepository.findByVacation(now);
        try {
            if (targetMember == null || targetMember.isEmpty()){
                log.info("scheduler= 당일 휴가 인원 없음");
            }
            for (AnnualLeaveEntity target : targetMember) {
                // 타켓의 ID 가져오고
                Long employeeId = target.getEmployee().getEmployeeId();
                // attendance 레포에 using true 시키고 저장
                AttendanceEntity attendanceEntity = new AttendanceEntity(employeeId, now, true);
                attendanceRepository.save(attendanceEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // 매년 1월 1일 공휴일 데이터 받기
    @Scheduled(cron = "0 */5 * * * *")
    public void getHolidayData(){
        LocalDateTime now = LocalDateTime.now();
        log.info("Scheduled getHolidayData Start = {}",now);
        int year = 2020;
        ResponseEntity<? super GetHolidayResponseDto> response = webClientService.setData(year);
        log.info("finish response={}",response);
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

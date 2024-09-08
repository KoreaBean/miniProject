package hello.miniproject.controller;

import hello.miniproject.dto.request.*;
import hello.miniproject.dto.response.*;
import hello.miniproject.scheduler.AnnualLeaveScheduler;
import hello.miniproject.service.TeamService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final TeamService teamService;
    // 팀 조회 기능
    @GetMapping("/team")
    public ResponseEntity<? super GetSearchTeamResponseDto> searchTeam(){
        ResponseEntity<? super GetSearchTeamResponseDto> response = teamService.searchTeams();
        return response;
    }
    // 팀 저장 기능
    @PostMapping("/team")
    public ResponseEntity<? super PostSaveTeamResponseDto> saveTeam(@RequestBody PostSaveTeamRequestDto dto){
        log.info("controller dto={}",dto);
        ResponseEntity<? super PostSaveTeamResponseDto> result = teamService.saveTeam(dto);
        return result;
    }
    // 직원 저장 기능
    @PostMapping("/member")
    public ResponseEntity<? super PostSaveEmployeeResponseDto> saveEmployee(@RequestBody PostSaveEmployeeRequestDto dto){
        log.info("controller member={}",dto);
        ResponseEntity<? super PostSaveEmployeeResponseDto> result = teamService.saveEmployee(dto);
        return  result;
    }
    // 직원 조회
    @GetMapping("/member")
    public ResponseEntity<? super GetSearchEmployeesResponseDto> findEmployee(){
        ResponseEntity<? super GetSearchEmployeesResponseDto> result = teamService.searchEmployees();
        return result;
    }

    //직원 팀 배정
    @PostMapping("/join")
    public ResponseEntity<? super PostJoinTeamResponseDto> employeeToTeam(@RequestBody PostJoinTeamRequestDto dto){
        ResponseEntity<? super PostJoinTeamResponseDto> result = teamService.EmployeeToTeam(dto);
        return result;
    }

    //출근 기능
    @PostMapping("/attendance")
    public ResponseEntity<? super PostAttendanceResponseDto> attendance(@RequestBody PostAttendanceRequestDto dto){
        ResponseEntity<? super PostAttendanceResponseDto> result = teamService.attendance(dto.getEmployeeId());
        return result;
    }

    //퇴근 기능
    @PostMapping("/leave")
    public ResponseEntity<? super PostLeaveResponseDto> leave(@RequestBody PostLeaveRequestDto dto){
        ResponseEntity<? super PostLeaveResponseDto> result = teamService.leave(dto);
        return  result;
    }
//
//    // 특정 직원의 날짜별 근무시간을 조회하는 기능
//    // 매개변수 직원 id, 2024-01
//    @PostMapping("/searchMonth")
//    public ResponseEntity<? super PostSearchDateOfWorkTimeResponseDto> searchMonth(@RequestBody PostSearchDateOfWorkTimeRequestDto dto){
//        ResponseEntity<? super PostSearchDateOfWorkTimeResponseDto> result = teamService.searchMonth(dto);
//        return result;
//    }
//
    // 특정 직원의 날짜별 근무시간을 조회하는 기능 V2
    // 매개변수 직원 ID, 날짜 (2024-01)
    @PostMapping("/searchMonth")
    public ResponseEntity<? super PostSearchDateOfWorkTimeResponseDtoV2> searchMonth(@RequestBody PostSearchDateOfWorkTimeRequestDtoV2 dto){
        teamService.searchMonthV2(dto);
        return null;
    }

    //연차 조회 기능
    @GetMapping("/annual-leave/{employeeId}")
    public ResponseEntity<? super GetSearchAnnualLeaveResponseDto> annualLeave(@PathVariable("employeeId") Long employeeId){
        ResponseEntity<? super GetSearchAnnualLeaveResponseDto> result = teamService.annualLeave(employeeId);
        return result;
    }

    // 연차 신청 기능
    @PostMapping("/annual-leave")
    public ResponseEntity<? super PostAnnualLeaveResponseDto> requestAnnualLeave(@RequestBody PostAnnualLeaveRequestDto dto){
        ResponseEntity<? super PostAnnualLeaveResponseDto> result = teamService.requestAnnualLeave(dto);
        return result;
    }





}

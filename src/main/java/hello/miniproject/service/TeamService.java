package hello.miniproject.service;

import hello.miniproject.dto.ResponseDto;
import hello.miniproject.dto.object.EmployeesListItem;
import hello.miniproject.dto.object.TeamListItem;
import hello.miniproject.dto.request.*;
import hello.miniproject.dto.response.*;
import hello.miniproject.entity.AnnualLeaveEntity;
import hello.miniproject.entity.AttendanceEntity;
import hello.miniproject.entity.EmployeeEntity;
import hello.miniproject.entity.TeamEntity;
import hello.miniproject.repository.AnnualLeaveRepository;
import hello.miniproject.repository.AttendanceRepository;
import hello.miniproject.repository.EmployeeRepository;
import hello.miniproject.repository.TeamEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamEntityRepository teamEntityRepository;
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final AnnualLeaveRepository annualLeaveRepository;

    // 팀 저장
    @Transactional
    public ResponseEntity<? super PostSaveTeamResponseDto> saveTeam(PostSaveTeamRequestDto dto){
        log.info("service TeamName={}",dto.getTeamName());

        try {
            // 동일한 팀명이 있을 경우
            boolean isTrue = teamEntityRepository.existsByTeamName(dto.getTeamName());
            if (isTrue){
                // -> 팀명 조회 -> 있다면 에러 발송
                log.info("service TeamService DUPLICATED_FAILD");
                return PostSaveTeamResponseDto.duplicatedByTeamName();
            }else {
                // -> 팀명 조회 -> 없다면 저장
                log.info("service TeamService SAVE");
                teamEntityRepository.save(new TeamEntity(dto.getTeamName()));
            }
        }catch (Exception e){
            // 데이터베이스 에러로 등록 못할 경우
            e.printStackTrace();
            log.info("service TeamService DB_FAILD");

            return PostSaveTeamResponseDto.databaseError();
        }
        // Http Status 200 발송
        log.info("service TeamService success");
        return PostSaveTeamResponseDto.success();
    }
    @Transactional
    // 직원 저장
    public ResponseEntity<? super PostSaveEmployeeResponseDto> saveEmployee(PostSaveEmployeeRequestDto dto){

        try {
            EmployeeEntity employeeEntity = new EmployeeEntity(dto);
            employeeRepository.save(employeeEntity);
            log.info("service saveEmployee save");
        }catch (Exception e){
            e.printStackTrace();
            log.info("service saveEmployee DBFAILD");
            return ResponseDto.databaseError();
        }
        log.info("service saveEmployee success");
        return PostSaveEmployeeResponseDto.success();
    }


    @Transactional
    // 팀 조회 기능
    public ResponseEntity<? super GetSearchTeamResponseDto> searchTeams(){
        List<TeamListItem> list = new ArrayList<>();
        // 모든 팀 조회
        try {
            list = teamEntityRepository.findAll().stream()
                    .map(team -> new TeamListItem(team.getTeamName(),
                            Optional.ofNullable(team.getManager()).map(EmployeeEntity::getName).orElse("NoManager"),
                            team.getEmployees().size()))
                    .collect(Collectors.toList());
        }catch (Exception e){
            // DB 에러
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        log.info("service , searchTeams ={}",list);
        return GetSearchTeamResponseDto.success(list);
    }

    @Transactional
    // 직원 팀 배정
    public ResponseEntity<? super PostJoinTeamResponseDto> EmployeeToTeam(PostJoinTeamRequestDto dto){
        EmployeeEntity employee = new EmployeeEntity();
        TeamEntity team = new TeamEntity();
        // ---
        try {
            // 1. 직원 조회
            Optional<EmployeeEntity> target = employeeRepository.findById(dto.getEmployeeId());
            // 1.1 -> 직원을 찾을 수 없는 경우
            if (!target.isPresent()){
                return PostJoinTeamResponseDto.noExistedUser();
            }else {
                // 1.2 -> 직원을 찾은 경우
                employee = target.get();
            }
            // 2. 팀 조회
            Optional<TeamEntity> team1 = teamEntityRepository.findById(dto.getTeamId());
            // 2.1 팀을 찾을 수 없는 경우
            if (!team1.isPresent()){
                return PostJoinTeamResponseDto.noExistedTeam();
            }else {
                // 2.2 팀을 찾은 경우
                team = team1.get();
            }
            employee.setTeam(team);
            employeeRepository.save(employee);
        }catch (Exception e){
            e.printStackTrace();
            // 3. 데이터베이스 에러
            return ResponseDto.databaseError();
        }
        // 200 코드 전송
        return PostJoinTeamResponseDto.success();
    }

    //직원들 검색
    @Transactional
    public ResponseEntity<? super GetSearchEmployeesResponseDto> searchEmployees(){
        List<EmployeesListItem> list = new ArrayList<>();

        try {
            // 직원들 검색
            list = employeeRepository.findAll().stream()
                    .map(target -> new EmployeesListItem(target.getName(),
                            Optional.ofNullable(target.getTeam()).map(TeamEntity::getTeamName).orElse("미배정"),
                            target.getRole(), target.getBirthday(),
                            target.getWorkStartDate(),
                            target.getAnnualLeaves(),
                            target.getVacation()))
                    .collect(Collectors.toList());
            // 검색 직원 중 team 에 소속되지 않은 사람들 null 값 미배정으로 변환
            // 직원 검색 되지 않을 시
            if (list == null ||list.isEmpty()){
                return GetSearchEmployeesResponseDto.noExistedUser();
            }
        }catch (Exception e){
            // 데이터베이스 에러 시
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        // status 200 전송
        return GetSearchEmployeesResponseDto.success(list);
    }

    // 출근 기능
    @Transactional
    public ResponseEntity<? super PostAttendanceResponseDto> attendance(Long employeeId) {
        EmployeeEntity employee = new EmployeeEntity();
        try {
            // 1. 없는 회원 일 경우 ex ) id 가 없는 회원
            Optional<EmployeeEntity> findEmployee = employeeRepository.findById(employeeId);
            if (!findEmployee.isPresent()){
                return PostAttendanceResponseDto.noExistedUser();
            }else {
                employee = findEmployee.get();
            }
            // 2. 출근한 직원이 퇴근 안하고 또 출근 찍을 경우
            boolean isAttendance = attendanceRepository.existsByEmployeeIdAndOutTimeIsNull(employee.getEmployeeId());
            if (isAttendance){
                return PostAttendanceResponseDto.noExistedOut();
            }
            // 4. 출근했다 퇴근한 직원이 다시 출근하는 경우 -> 통과
            attendanceRepository.save(new AttendanceEntity(employee.getEmployeeId()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostAttendanceResponseDto.success();
        // 다 통과 시 save

    }

    // 퇴근 기능
    @Transactional
    public ResponseEntity<? super PostLeaveResponseDto> leave(PostLeaveRequestDto dto){

        try {
            // 1. 유저 조회
            Optional<EmployeeEntity> findEmployee = employeeRepository.findById(dto.getEmployeeId());
            // 2. 유저가 없는 경우
            if (!findEmployee.isPresent()){
                return PostLeaveResponseDto.noExistedUser();
            }
            // 3. 유저가 있는 경우 -> 다음 스텝
            // 4. 출근 찍은게 없는데 퇴근 하려는 경우
            boolean isAttendance = attendanceRepository.existsByEmployeeIdAndOutTimeIsNull(dto.getEmployeeId());
            if (!isAttendance){
                return PostLeaveResponseDto.noExistedAttendance();
            }
            // 5. save
            Optional<AttendanceEntity> employee = attendanceRepository.findByEmployeeIdAndOutTimeIsNull(dto.getEmployeeId());
            if (employee.isPresent()){
                AttendanceEntity attendanceEntity = employee.get();
                attendanceEntity.setOutTime();
                attendanceRepository.save(attendanceEntity);
            }else {
                return PostLeaveResponseDto.noExistedAttendance();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return PostLeaveResponseDto.success();
    }
//
//    //특정 직원의 날짜별 근무시간을 조회하는 기능
//    public ResponseEntity<? super PostSearchDateOfWorkTimeResponseDto> searchMonth(PostSearchDateOfWorkTimeRequestDto dto) {
//        EmployeeEntity employee = new EmployeeEntity();
//        List<AttendanceEntity> list = new ArrayList<>();
//        try {
//            // 특정 직원 데이터베이스 서치
//            Optional<EmployeeEntity> targetImployee = employeeRepository.findById(dto.getEmployeeId());
//            // 특정 직원이 없다면?(퇴사했다면) 에러 발생
//            if (!targetImployee.isPresent()) {
//                return PostSearchDateOfWorkTimeResponseDto.noExistedUser();
//            } else {
//                employee = targetImployee.get();
//            }
//            list = attendanceRepository.findByEmployeeIdAndEnterTimeStartingWith(dto.getEmployeeId(), dto.getDate());
//            if (list == null || list.isEmpty()) {
//                // 특정 직원의 출퇴근 기록이 없다면? 에러발생
//                return PostSearchDateOfWorkTimeResponseDto.noExistedData();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseDto.databaseError();
//        }
//        // 특정 직원의 월간 출퇴근 기록 가져옴
//        // 데이터 가공
//        // 200 반환
//        return PostSearchDateOfWorkTimeResponseDto.success(list);
//    }

    // 연차 조회 기능 - 직원 id로 남은 연차 확인
    public ResponseEntity<? super GetSearchAnnualLeaveResponseDto> annualLeave(Long employeeId){
        EmployeeEntity target = new EmployeeEntity();

        try {
            // 유저 조회
            Optional<EmployeeEntity> employee = employeeRepository.findById(employeeId);
            if (!employee.isPresent()){
                // 유저 조회 x
                return GetSearchAnnualLeaveResponseDto.noExistedUser();
            }else {
                target = employee.get();
            }
            // 유저 조회 -> 유저 값 자체를 반환
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetSearchAnnualLeaveResponseDto.success(target);
    }
    @Transactional
    // 연차 신청 기능 , 유저Id, 신청요청일, 신청일
    public ResponseEntity<? super PostAnnualLeaveResponseDto> requestAnnualLeave(PostAnnualLeaveRequestDto dto) {
        EmployeeEntity employee = new EmployeeEntity();

        try {
            // 유저 데이터 가져옴
            Optional<EmployeeEntity> findUser = employeeRepository.findById(dto.getEmployeeId());
            if (!findUser.isPresent()) {
                return PostAnnualLeaveResponseDto.noExistedUser();
            }else {
                employee = findUser.get();
            }

            List<LocalDate> requestAnnual = dto.getAnnualLeaveDate();
            LocalDate requestDay = dto.getRequestDay();

            // 유저 데이터에 연차 남아 있는지 확인 - 신청하려는 연차 일
            if (employee.getAnnualLeaves() < requestAnnual.size()){
                return PostAnnualLeaveResponseDto.invalid("남은 연차가 부족합니다.");
            }

            // A 팀은 최소 하루 전 신청, B팀은 최소 일주일 전 신청
            for (LocalDate localDate : requestAnnual) {
                if (employee.getTeam().getTeamId() == 1){
                    // 신청일은 9월9일 신청 휴가는 9월 10일 == 9월9일이 9월10일 하루 이전이냐?
                    if (!requestDay.isBefore(localDate.minusDays(1))){
                        return PostAnnualLeaveResponseDto.invalid("A팀은 최소 하루 전 신청");
                    }
                }
                if (employee.getTeam().getTeamId() == 2){
                    if (!requestDay.isBefore(localDate.minusDays(7))){
                        return PostAnnualLeaveResponseDto.invalid("B팀은 최소 일주일 전 신청");
                    }
                }
                annualLeaveRepository.save(new AnnualLeaveEntity(employee,localDate));
            }
            // 성공
            employee.setAnnualLeaves(employee.getAnnualLeaves() - requestAnnual.size());
            employeeRepository.save(employee);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostAnnualLeaveResponseDto.success();
    }




    // 특정 직원의 날짜별 근무시간 조회 V2
    // 연차를 사용했다면 usingDayOff : true

}

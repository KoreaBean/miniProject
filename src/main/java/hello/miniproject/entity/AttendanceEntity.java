package hello.miniproject.entity;

import hello.miniproject.entity.primarykey.AttendancePk;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@Entity(name = "attendance")
@Getter
@Setter
public class AttendanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;
    private Long employeeId;
    private String  date;
    private String enterTime;
    private String outTime;
    private Boolean usingDatOff = false;


    public AttendanceEntity() {
    }
    // 출근기록 생성자
    public AttendanceEntity(Long id) {

        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formatTime = currentTime.format(formatter);


        this.employeeId = id;
        this.date = String.valueOf(LocalDate.now());
        this.enterTime = formatTime;
    }


    public AttendanceEntity(Long employeeId, LocalDate now, boolean isTrue) {
        this.employeeId = employeeId;
        this.date = String.valueOf(now);
        this.usingDatOff = isTrue;
    }

    public void setOutTime(){
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formatTime = currentTime.format(formatter);

        this.outTime = formatTime;
    }
}

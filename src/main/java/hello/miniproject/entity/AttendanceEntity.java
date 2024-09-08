package hello.miniproject.entity;

import hello.miniproject.entity.primarykey.AttendancePk;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Entity(name = "attendance")
@IdClass(AttendancePk.class)
@Getter
@Setter
public class AttendanceEntity {

    @Id
    private Long employeeId;
    @Id
    private String enterTime;

    private String outTime;


    public AttendanceEntity() {
    }

    public AttendanceEntity(Long id) {
        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(now);

        this.employeeId = id;
        this.enterTime = date;
    }
    public void setOutTime(){
        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(now);

        this.outTime = date;
    }
}

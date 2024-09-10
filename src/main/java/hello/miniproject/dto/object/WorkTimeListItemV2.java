package hello.miniproject.dto.object;

import hello.miniproject.entity.AttendanceEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
public class WorkTimeListItemV2 {
    private LocalDate date;
    private Long workingMinutes;
    private Boolean usingDayOff;

    public WorkTimeListItemV2(LocalDate targetDate, long minutes, Boolean usingDatOff) {
        this.date = targetDate;
        this.workingMinutes = minutes;
        this.usingDayOff = usingDatOff;
    }

    public static List<WorkTimeListItemV2> getList(List<AttendanceEntity> targetList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<WorkTimeListItemV2> list = new ArrayList<>();

        for (AttendanceEntity target : targetList) {

            LocalDate targetDate = LocalDate.parse(target.getDate());
            LocalTime enter = LocalTime.parse(target.getEnterTime());
            LocalTime out = LocalTime.parse(target.getOutTime());
            long minutes = Duration.between(enter, out).toMinutes();
            Boolean usingDatOff = target.getUsingDatOff();

            WorkTimeListItemV2 item = new WorkTimeListItemV2(targetDate, minutes, usingDatOff);
            list.add(item);
        }


        return list;
    }

}

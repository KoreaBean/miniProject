package hello.miniproject.entity.primarykey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendancePk implements Serializable {

    private Long employeeId;
    private String enterTime;


}

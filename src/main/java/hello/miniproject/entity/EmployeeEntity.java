package hello.miniproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.miniproject.common.Role;
import hello.miniproject.dto.request.PostSaveEmployeeRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity(name = "employee")
@Getter
@Setter
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    private String name;
    @ManyToOne
    @JsonIgnore
    private TeamEntity team;
    private Role role;
    private String birthday;
    private String workStartDate;
    private Integer annualLeaves; // 연차

    @OneToOne(mappedBy = "manager")
    private TeamEntity manager;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private List<AnnualLeaveEntity> vacation;


    public EmployeeEntity() {
    }

    public EmployeeEntity(PostSaveEmployeeRequestDto dto) {
        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(now);

        this.name = dto.getName();
        this.role = dto.getManager();
        this.workStartDate = date;
        this.birthday = dto.getBirthDay();
        this.annualLeaves = 11;
    }

}

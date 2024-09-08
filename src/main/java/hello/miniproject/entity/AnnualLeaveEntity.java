package hello.miniproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.miniproject.entity.primarykey.AnnualLeavePk;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "employee_vacation")
@IdClass(AnnualLeavePk.class)
public class AnnualLeaveEntity {

    @ManyToOne
    @JsonIgnore
    @Id
    private EmployeeEntity employee;
    @Id
    private LocalDate vacation;

    public AnnualLeaveEntity() {
    }

    public AnnualLeaveEntity(EmployeeEntity employee, LocalDate vacation) {
        this.employee = employee;
        this.vacation = vacation;
    }
}

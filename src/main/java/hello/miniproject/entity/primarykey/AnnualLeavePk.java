package hello.miniproject.entity.primarykey;


import hello.miniproject.entity.EmployeeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeavePk implements Serializable {

    private EmployeeEntity employee;
    private LocalDate vacation;



}

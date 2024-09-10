package hello.miniproject.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PostSearchDateOfWorkTimeRequestDtoV2 {
    private Long employeeId;
    private String requestMonth;
}

package hello.miniproject.dto.request;

import hello.miniproject.common.Role;
import lombok.Data;

@Data
public class PostSaveEmployeeRequestDto {

    private String name;
    private Role manager;
    private String birthDay;



}

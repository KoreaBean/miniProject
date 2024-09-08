package hello.miniproject.dto.request;

import lombok.Data;

@Data
public class PostJoinTeamRequestDto {

    private Long teamId;
    private Long employeeId;

}

package hello.miniproject.dto.object;

import hello.miniproject.entity.TeamEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class TeamListItem {

    private String teamName;
    private String manager = null;
    private Integer memberCount;


}

package hello.miniproject.dto.response;

import hello.miniproject.common.ResponseCode;
import hello.miniproject.common.ResponseMessage;
import hello.miniproject.dto.ResponseDto;
import hello.miniproject.dto.object.TeamListItem;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Slf4j
public class GetSearchTeamResponseDto extends ResponseDto {

    private List<TeamListItem> list ;

    public GetSearchTeamResponseDto( List<TeamListItem> list) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        log.info("GetSearchTeamResponseDto list={}",list);
        this.list = list;
    }

    public static ResponseEntity<GetSearchTeamResponseDto> success(List<TeamListItem> list){
        GetSearchTeamResponseDto result = new GetSearchTeamResponseDto(list);
        log.info("list result ={}",result.list);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}

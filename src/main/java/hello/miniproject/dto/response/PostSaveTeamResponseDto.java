package hello.miniproject.dto.response;


import hello.miniproject.common.ResponseCode;
import hello.miniproject.common.ResponseMessage;
import hello.miniproject.dto.ResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostSaveTeamResponseDto extends ResponseDto {


    public PostSaveTeamResponseDto(String code, String message) {
        super(code, message);
    }


    public static ResponseEntity<PostSaveTeamResponseDto> success(){

        PostSaveTeamResponseDto result = new PostSaveTeamResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> duplicatedByTeamName(){
        ResponseDto result = new ResponseDto(ResponseCode.DUPLICATE_TEAM_NAME, ResponseMessage.DUPLICATE_TEAM_NAME);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

}

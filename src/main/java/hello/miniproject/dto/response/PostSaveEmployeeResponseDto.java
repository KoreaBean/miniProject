package hello.miniproject.dto.response;

import hello.miniproject.common.ResponseCode;
import hello.miniproject.common.ResponseMessage;
import hello.miniproject.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PostSaveEmployeeResponseDto extends ResponseDto {

    public PostSaveEmployeeResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }


    public static ResponseEntity<PostSaveEmployeeResponseDto> success(){
        PostSaveEmployeeResponseDto result = new PostSaveEmployeeResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }



}

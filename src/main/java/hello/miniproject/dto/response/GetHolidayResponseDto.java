package hello.miniproject.dto.response;

import hello.miniproject.common.ResponseCode;
import hello.miniproject.common.ResponseMessage;
import hello.miniproject.dto.ResponseDto;
import hello.miniproject.dto.object.HolidayItemList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class GetHolidayResponseDto extends ResponseDto{


    public GetHolidayResponseDto() {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }


    public static ResponseEntity<? super GetHolidayResponseDto> success(){
        GetHolidayResponseDto response = new GetHolidayResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static ResponseEntity<ResponseDto> fail(String message){
        ResponseDto response = new ResponseDto(ResponseCode.NO_PERMISSION, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}

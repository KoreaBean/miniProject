package hello.miniproject.dto.response;

import hello.miniproject.common.ResponseCode;
import hello.miniproject.common.ResponseMessage;
import hello.miniproject.common.Role;
import hello.miniproject.dto.ResponseDto;
import hello.miniproject.dto.object.EmployeesListItem;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetSearchEmployeesResponseDto extends ResponseDto {

    private List<EmployeesListItem> list ;

    public GetSearchEmployeesResponseDto(List<EmployeesListItem> list) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.list = list;
    }


    public static ResponseEntity<? super GetSearchEmployeesResponseDto> success(List<EmployeesListItem> list) {
        GetSearchEmployeesResponseDto response = new GetSearchEmployeesResponseDto(list);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    public static ResponseEntity<? super GetSearchEmployeesResponseDto> noExistedUser() {

        ResponseDto response = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }
}

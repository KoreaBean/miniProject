package hello.miniproject.dto.object;

import lombok.Data;

@Data
public class HolidayItemList {

    private Integer date;
    private String dateName;


    public HolidayItemList(Integer locdate, String dateName) {
        this.date = locdate;
        this.dateName = dateName;
    }
}

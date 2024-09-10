package hello.miniproject.dto.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public class HolidayResponse {

    private ApiResponse response;

    @Data
    public static class ApiResponse{

        private Header header;
        private Body body;

    }
    @Data
    @NoArgsConstructor
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }
    @Data
    @NoArgsConstructor
    public static class Body{
        @JsonDeserialize(using = ItemDeserializer.class)
        private List<Item> items;
        private Integer numOfRows;
        private Integer pageNo;
        private Integer totalCount;
    }
    @Data
    @NoArgsConstructor
    public static class Item{
        @JsonProperty("dataKind")
        private String dateKind;
        @JsonProperty("dateName")
        private String dateName;
        @JsonProperty("isHoliday")
        private String isHoliday;
        @JsonProperty("locdate")
        private Integer locdate;
        @JsonProperty("seq")
        private Integer seq;
    }
    /**
     * items 필드의 JSON 배열을 List<Item>으로 변환하는 Deserializer
     * API에서 결과 값을 반환할 때, 공휴일이 1개라면 {} 로 반환하고 2개 이상이면 [] 로 반환하기에 별도의 Deserializer 생성
     */
    public static class ItemDeserializer extends JsonDeserializer<List<Item>>{
        @Override
        public List<Item> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            log.info("deserialize start");
            ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            List<HolidayResponse.Item> items = new ArrayList<>();

            JsonNode itemNode = node.get("item");
            log.info("itemNode isArray={}",itemNode.isArray());

            if (itemNode.isArray()){
                for (JsonNode targetNode : itemNode) {
                    Item item = mapper.treeToValue(targetNode, Item.class);
                    items.add(item);
                }
            }else {
                log.warn("itemNode is not an Array");
            }
            log.info("items={}",items);
            return items;
        }

    }

}

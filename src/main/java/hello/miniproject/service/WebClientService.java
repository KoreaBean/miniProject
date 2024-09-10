package hello.miniproject.service;

import hello.miniproject.dto.ResponseDto;
import hello.miniproject.dto.object.HolidayItemList;
import hello.miniproject.dto.object.HolidayResponse;
import hello.miniproject.dto.response.GetHolidayResponseDto;
import hello.miniproject.entity.HolidayEntity;
import hello.miniproject.repository.HolidayRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebClientService {

    private final HolidayRepository holidayRepository;

    // http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getHoliDeInfo?solYear=2019&solMonth=03&ServiceKey=서비스키
    private WebClient webClient;
    @Value("${holiday_key}")
    private String API_KEY;
    @Value("${OPEN_API_URL}")
    private String OPEN_API_URL;

    @PostConstruct
    public void initWebClient(){
        final DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(OPEN_API_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        this.webClient = WebClient.builder()
                .uriBuilderFactory(factory)
                .baseUrl(OPEN_API_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
                .build();

    }

    @Transactional
    public ResponseEntity<? super GetHolidayResponseDto> setData(int year){
        // return list
        List<HolidayItemList> list = new ArrayList<>();
        try {
            // URI 객체를 먼저 생성
            // URI 생성 - uri 가 내가 원하는데로 전송되는지 확인하기 위한 코드
            URI uri = UriComponentsBuilder.fromUriString(OPEN_API_URL)
                    .queryParam("solYear", year)
                    .queryParam("ServiceKey", API_KEY)
                    .queryParam("_type", "json")
                    .queryParam("numOfRows", 20)
                    .build()
                    .toUri();
            // 로그로 URI 출력
            log.info("Generated URI: {}", uri);

            HolidayResponse response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("solYear", year)
                            .queryParam("ServiceKey", API_KEY)
                            .queryParam("_type", "json")
                            .queryParam("numOfRows", 20)
                            .build())
                    .header(HttpHeaders.ACCEPT,MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(HolidayResponse.class)
                    .block();
            log.info("response={}",response);

            List<HolidayResponse.Item> items = response.getResponse().getBody().getItems();
            for (HolidayResponse.Item item : items) {
                holidayRepository.save(new HolidayEntity(item.getLocdate(), item.getDateName()));
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetHolidayResponseDto.success();
    }
}

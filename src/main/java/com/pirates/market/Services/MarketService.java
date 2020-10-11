package com.pirates.market.Services;

import com.pirates.market.Domain.*;
import com.pirates.market.Domain.VO.DetailVO;
import com.pirates.market.Domain.VO.ListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MarketService {
    private MarketRepository marketRepository = null;
    private Date nTime;
    private String nDate = null;
    private String nWeek = null;

    @Autowired
    public MarketService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;

        // 현재 날짜와 시간
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar now = Calendar.getInstance();

        String now_date = date_format.format(now.getTime());
        String[] time_str = now_date.split("\\s");         // 날짜, 시간
        nDate = time_str[0];

        // 시간끼리 비교하기 위해 분
        SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");

        try{
            nTime = time_format.parse(time_str[1]);
        }catch(ParseException e){
            e.printStackTrace();
        }

        // 현재 요일
        int weekNum = now.get(Calendar.DAY_OF_WEEK) - 1;
        String[] weekDay = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        nWeek = weekDay[weekNum];
    }

    public String getBusinessStatus(BusinessTime businessTime){
        SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");
        Date open = null;
        Date closed = null;

        try{
            open = time_format.parse(businessTime.getOpen());
            closed = time_format.parse(businessTime.getClose());

            // 해당 요일이 존재하면
            if(businessTime.getDay().equals(nWeek)) {
                // 시간 비교
                if (nTime.getTime() >= open.getTime() && nTime.getTime() <= closed.getTime()) {
                    return "OPEN";
                }
            }
        }catch(ParseException e) {
            e.printStackTrace();
        }

        return "CLOSE";
    }

    public String getBusiness(Market market){
        SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");

        List<Holiday> holidays = market.getHolidays();
        // 휴일중 지금 날짜와 같은 것이 있는지 카운트
        boolean check = false;

        if(holidays != null) {
            check = holidays.stream().anyMatch(n -> n.getDate().equals(nDate));
        }
        String businessStatus = "CLOSE";

        // 휴일일 경우,
        if(check){
            businessStatus = "HOLIDAY";
        }
        else{
            List<BusinessTime> businessTimes = market.getBusinessTimes();
            // business 정보가 있을 경우,
            if(businessTimes != null){
                for(BusinessTime b : businessTimes){
                    if(getBusinessStatus(b).equals("OPEN")){
                        businessStatus = "OPEN";
                        break;
                    }
                }
            }
        }

        return businessStatus;
    }

    // 마켓들의 리스트, BusinessStatus 상태
    public List<ListVO> getMarketList(){
        // 마켓들의 정보를 Level 순으로 정렬
        List<Market> markets = marketRepository.findAll();
        markets = markets.stream().sorted().collect(Collectors.toList());

        List<ListVO> listVOList = new ArrayList<>();

        // market들의 정보 중, 휴일인지 영업중인지 확인
        for(Market market : markets){
            String businessStatus = getBusiness(market);

            listVOList.add(ListVO.builder().name(market.getName())
                                            .level(market.getLevel())
                                            .description(market.getDescription())
                                            .businessStatus(businessStatus)
                                            .build());
        }

        return listVOList;
    }

    public DetailVO getMarketDetail(Integer id){
        DetailVO detailMarket;

        // 해당 마켓 정보
        Market market = marketRepository.findById(id).orElseThrow(() -> new MarketNotFoundException(id));
        List<Holiday> holidays = market.getHolidays();  // 휴일 정보

        Calendar now = Calendar.getInstance();
        int weekNum = now.get(Calendar.DAY_OF_WEEK) - 1;

        String[] weekDay = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        String[] week_list = new String[3];     // 3일 요일
        String[] date_list = new String[3];     // 3일 날짜

        // 현재 날짜 기준으로 3개의 요일과 날짜를 저장.
        for(int i=0; i<3; i++){
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH) + 1;
            int day = now.get(Calendar.DAY_OF_MONTH);
            week_list[i] = weekDay[(weekNum + i) % 7];

            date_list[i] = year + "-" + month + "-" + day;
            now.set(Calendar.DAY_OF_MONTH, day + 1);
        }

        List<BusinessTime> businessTimes = market.getBusinessTimes();
        List<BusinessTime> businessDays = new ArrayList<>();
        if(businessTimes != null) {
            for(int i = 0; i < 3; i++){
                int finalI = i;
                // 순차대로 근무가능 날짜 중, 해당하는 요일이 존재한다면 정보를 가져옴.
                Optional<BusinessTime> check_day = businessTimes.stream().filter(t -> t.getDay().equals(week_list[finalI])).findFirst();
                BusinessTime b_day = null;

                // 값이 존재할 경우, 값을 get
                if(check_day.isPresent()){
                    b_day = check_day.get();
                }

                // 해당하는 근무시간이 없으면
                if(b_day != null) {
                    b_day.setStatus(getBusinessStatus(b_day));

                    // 휴일이 있을 경우, 비교
                    if(holidays != null) {
                        for (Holiday holiday : holidays) {
                            if (holiday.getDate().equals(date_list[finalI])) {
                                b_day.setStatus("HOLIDAY");
                                break;
                            }
                        }
                    }
                    businessDays.add(b_day);
                }
            }
        }

        detailMarket = DetailVO.builder().level(market.getLevel())
                                         .id(id)
                                         .address(market.getAddress())
                                         .description(market.getDescription())
                                         .owner(market.getOwner())
                                         .phone(market.getPhone())
                                         .name(market.getName())
                                         .businessDays(businessDays)
                                         .build();

        return detailMarket;
    }

    // 수산시장 추가
    public Market addMarket(Market market){
        return marketRepository.save(market);
    }

    // 휴무일 설정
    @Transactional
    public Market setHolidays(Integer id, List<Holiday> holidays){
        // 해당하는 id가 존재하지 않으면, 예외처리 전송
        Market market = marketRepository.findById(id).orElseThrow(() -> new MarketNotFoundException(id));
        market.setHolidays(holidays);

        return market;
    }
}

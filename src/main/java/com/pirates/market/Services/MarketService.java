package com.pirates.market.Services;

import com.pirates.market.Domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarketService {
    private MarketRepository marketRepository;

    @Autowired
    public MarketService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    // 현재 날짜와 시간 - String 반환
    public String[] getDateString(){
        // 현재 날짜와 시간
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar now = Calendar.getInstance();

        String now_date = date_format.format(now.getTime());
        return now_date.split("\\s");         // 날짜, 시간
    }
    // 현재 시간
    public Date getTime(){
        String time_str = getDateString()[1];
        // 시간끼리 비교하기 위해 분
        SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");
        Date time = null;
        try{
            time = time_format.parse(time_str);
        }catch(ParseException e){
            e.printStackTrace();
        }

        return time;
    }
    // 현재 요일
    public String getWeek(){
        Calendar now = Calendar.getInstance();
        int weekNum = now.get(Calendar.DAY_OF_WEEK) - 1;
        String[] weekDay = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        return weekDay[weekNum];
    }


    public String getBusinessStatus(Market market){
        Date time = getTime();
        String date = getDateString()[0];

        SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");

        List<Holiday> holidays = market.getHolidays();
        // 휴일중 지금 날짜와 같은 것이 있는지 카운트
        long count = 0;

        if(holidays != null) {
            count = holidays.stream().filter(t -> t.getDate().equals(date)).count();
        }
        String businessStatus = null;

        // 휴일일 경우,
        if(count != 0){
            businessStatus = "HOLIDAY";
        }
        else{
            List<BusinessTime> businessTimes = market.getBusinessTimes();
            // business 정보가 있을 경우,
            if(businessTimes != null){
                for(BusinessTime b : businessTimes){
                    Date open = null;
                    Date closed = null;
                    try{
                        open = time_format.parse(b.getOpen());
                        closed = time_format.parse(b.getClose());

                        // 시간 비교
                        if( time.getTime() >= open.getTime() && time.getTime() <= closed.getTime() ){
                            businessStatus = "OPEN";
                        }
                        else{
                            businessStatus = "CLOSE";
                        }
                    }catch(ParseException e) {
                        e.printStackTrace();
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
            String businessStatus = getBusinessStatus(market);

            listVOList.add(ListVO.builder().name(market.getName())
                                            .level(market.getLevel())
                                            .description(market.getDescription())
                                            .businessStatus(businessStatus)
                                            .build());
        }

        return listVOList;
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

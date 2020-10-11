package com.pirates.market.Domain.VO;

import com.pirates.market.Domain.Holiday;
import lombok.Data;

import java.util.List;

@Data
public class HolidayVO {
    private Integer id;
    private List<Holiday> holidays;
}

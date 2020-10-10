package com.pirates.market.Domain;

import lombok.Data;

import java.util.List;

@Data
public class HolidayVO {
    private Integer id;
    private List<Holiday> holidays;
}

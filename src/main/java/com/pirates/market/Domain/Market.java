package com.pirates.market.Domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Market implements Comparable<Market>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer level;

    private String name;
    private String owner;
    private String address;
    private String phone;
    private String description;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private List<Holiday> holidays;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private List<BusinessTime> businessTimes;

    public void setHolidays(List<Holiday> holidays){
        this.holidays = holidays;
    }
    public void setBusinessTimes(List<BusinessTime> businessTimes){
        this.businessTimes = businessTimes;
    }

    @Override
    public int compareTo(Market o) {
        return Integer.compare(this.level, o.getLevel());
    }
}

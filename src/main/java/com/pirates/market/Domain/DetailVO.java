package com.pirates.market.Domain;

import lombok.Data;

import java.util.List;

@Data
public class DetailVO {
    private Integer id;

    private Integer level;

    private String name;
    private String owner;
    private String address;
    private String phone;
    private String description;

    private List<BusinessTime> businessDays;
}

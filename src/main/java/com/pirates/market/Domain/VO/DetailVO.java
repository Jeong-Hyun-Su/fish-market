package com.pirates.market.Domain.VO;

import com.pirates.market.Domain.BusinessTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

package com.pirates.market.Domain.VO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListVO {
    private String name;
    private String description;
    private Integer level;
    private String businessStatus;
}

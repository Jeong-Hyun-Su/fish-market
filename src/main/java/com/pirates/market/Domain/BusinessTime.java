package com.pirates.market.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class BusinessTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    private String day;
    @Getter
    private String open;
    @Getter
    private String close;

    @Getter
    @Setter
    private String status;

    public BusinessTime(String day, String open, String close) {
        this.day = day;
        this.open = open;
        this.close = close;
    }

}

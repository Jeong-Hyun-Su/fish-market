package com.pirates.market.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class BusinessTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String day;
    private String open;
    private String close;

    @Setter
    private String status;

    public BusinessTime(String day, String open, String close) {
        this.day = day;
        this.open = open;
        this.close = close;
    }

}

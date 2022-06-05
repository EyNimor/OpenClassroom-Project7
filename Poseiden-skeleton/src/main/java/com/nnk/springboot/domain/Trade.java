package com.nnk.springboot.domain;

import java.sql.Timestamp;

import javax.persistence.*;

import com.nnk.springboot.annotation.ExcludeFromJacocoGeneratedReport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "trade")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString
@ExcludeFromJacocoGeneratedReport
public class Trade {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer tradeId ;
    private String account ;
    private String type ;
    private Double buyQuantity ;
    private Double sellQuantity ;
    private Double buyPrice ;
    private Double sellPrice ;
    private String benchmark ;
    private Timestamp tradeDate ;
    private String security ;
    private String status ;
    private String trader ;
    private String book ;
    private String creationName ;
    private Timestamp creationDate ;
    private String revisionName ;
    private Timestamp revisionDate ;
    private String dealName ;
    private String dealType ;
    private String sourceListId ;
    private String side ;

    public Trade(Object object) {
        Trade casted = (Trade) object;
        this.setTradeId(casted.getTradeId());
        this.setAccount(casted.getAccount());
        this.setType(casted.getType());
        this.setBuyQuantity(casted.getBuyQuantity());
    }

    public Trade(String account, String type, Double buyQuantity) {
        this.setAccount(account);
        this.setType(type);
        this.setBuyQuantity(buyQuantity);
    }

}

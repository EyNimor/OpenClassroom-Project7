package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nnk.springboot.annotation.ExcludeFromJacocoGeneratedReport;

import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString
@ExcludeFromJacocoGeneratedReport
public class BidList { 

    private static final Logger logger = LogManager.getLogger("BidList");

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer bidListId ;
    @NotBlank(message = "Account is mandatory")
    private String account ;
    @NotBlank(message = "Type is mandatory")
    private String type ;
    private Double bidQuantity ;
    private Double askQuantity ;
    private Double bid ;
    private Double ask ;
    private String benchmark ;
    private Timestamp bidListDate ;
    private String commentary ;
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

    public BidList(Object object) {
        BidList casted = (BidList) object;
        this.setBidListId(casted.getBidListId());
        this.setAccount(casted.getAccount());
        this.setType(casted.getType());
        if(casted.getBidQuantity() == null) {
            logger.info("BidQuantity = null : passage à 0.0");
            this.setBidQuantity(0.0);
        }
        else {
            this.setBidQuantity(casted.getBidQuantity());
        }
    }

    public BidList(@NotBlank(message = "Account is mandatory") String account,
            @NotBlank(message = "Type is mandatory") String type, Double bidQuantity) {
        this.setAccount(account);
        this.setType(type);
        if(bidQuantity == null) {
            logger.info("BidQuantity = null : passage à 0.0");
            this.setBidQuantity(0.0);
        }
        else {
            this.setBidQuantity(bidQuantity);
        }
    }

}

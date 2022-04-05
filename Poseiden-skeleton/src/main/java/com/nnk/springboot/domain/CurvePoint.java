package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class CurvePoint {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id ;
    private Integer curveId ;
    private Timestamp asOfDate ;
    private Double term ;
    private Double value ;
    private Timestamp creationDate ;

}

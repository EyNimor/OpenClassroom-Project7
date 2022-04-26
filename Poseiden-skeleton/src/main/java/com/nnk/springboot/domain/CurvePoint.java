package com.nnk.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class CurvePoint {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id ;
    @NotNull(message = "Curve ID must not be null")
    private Integer curveId ;
    private Timestamp asOfDate ;
    private Double term ;
    private Double value ;
    private Timestamp creationDate ;

    public CurvePoint(Object object) {
        CurvePoint casted = (CurvePoint) object;
        this.setId(casted.getId());
        this.setCurveId(casted.getCurveId());
        this.setTerm(casted.getTerm());
        this.setValue(casted.getValue());
    }

    @Override
    public String toString() {
        return "CurvePoint [asOfDate=" + asOfDate + ", creationDate=" + creationDate + ", curveId=" + curveId + ", id="
                + id + ", term=" + term + ", value=" + value + "]";
    }

}

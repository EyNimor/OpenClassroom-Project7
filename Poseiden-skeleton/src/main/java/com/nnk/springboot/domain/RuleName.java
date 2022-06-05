package com.nnk.springboot.domain;

import javax.persistence.*;

import com.nnk.springboot.annotation.ExcludeFromJacocoGeneratedReport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "rulename")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString
@ExcludeFromJacocoGeneratedReport
public class RuleName {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id ;
    private String name ;
    private String description ;
    private String json ;
    private String template ;
    private String sqlStr ;
    private String sqlPart ;

    public RuleName(Object object) {
        RuleName casted = (RuleName) object;
        this.setId(casted.getId());
        this.setName(casted.getName());
        this.setDescription(casted.getDescription());
    }

    public RuleName(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }

}

package com.nnk.springboot.domain;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rulename")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
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

}

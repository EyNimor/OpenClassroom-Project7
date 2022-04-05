package com.nnk.springboot.domain;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rating")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Rating {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id ;
    private String moodysRating ;
    private String sandPRating ;
    private String fitchRating ;
    private Integer orderNumber ;

}

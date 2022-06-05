package com.nnk.springboot.domain;

import javax.persistence.*;

import com.nnk.springboot.annotation.ExcludeFromJacocoGeneratedReport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "rating")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString
@ExcludeFromJacocoGeneratedReport
public class Rating {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id ;
    private String moodysRating ;
    private String sandPRating ;
    private String fitchRating ;
    private Integer orderNumber ;

    public Rating(Object object) {
        Rating casted = (Rating) object;
        this.setId(casted.getId());
        this.setMoodysRating(casted.getMoodysRating());
        this.setSandPRating(casted.getSandPRating());
        this.setFitchRating(casted.getFitchRating());
    }

    public Rating(String moodysRating, String sandPRating, String fitchRating) {
        this.setMoodysRating(moodysRating);
        this.setSandPRating(sandPRating);
        this.setFitchRating(fitchRating);
    }

}

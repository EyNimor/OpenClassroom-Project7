package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.nnk.springboot.annotation.ExcludeFromJacocoGeneratedReport;
import com.nnk.springboot.annotation.ValidPassword;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor @ToString
@ExcludeFromJacocoGeneratedReport
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @ValidPassword
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    private String role;

    public User(Object object) {
        User casted = (User) object;
        this.setId(casted.getId());
        this.setUsername(casted.getUsername());
        this.setPassword(casted.getPassword());
        this.setFullname(casted.getFullname());
        this.setRole(casted.getRole());
    }

    public User(@NotBlank(message = "Username is mandatory") String username,
            @NotBlank(message = "Password is mandatory") String password,
            @NotBlank(message = "FullName is mandatory") String fullname,
            @NotBlank(message = "Role is mandatory") String role) {
        this.setUsername(username);
        this.setPassword(password);
        this.setFullname(fullname);
        this.setRole(role);
    }

}

package com.example.amazon.Model;

import com.example.amazon.Repository.UserRepository;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message="The User Name Cannot Be Empty")
    @Size(min=5,message="The User Name Must Be At Least Length of 5")
    @Column(columnDefinition = "varchar(20) not null")
    private String userName;
    @NotEmpty(message="The Password Cannot Be Empty")
    @Size(min=6,message="The Password  Must Be At Least Length of 6")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$",message = "The Password Must Contain Both Digits and Characters")
    @Column(columnDefinition = "varchar(15) not null")
    private String password;
    @NotEmpty(message = "The Email Cannot Be Empty")
    @Email(message = "The Email Must Be a Valid Format: name@gmail.com")
    @Column(columnDefinition = "varchar(30) not null")
    private String email;
    @NotEmpty(message="The User Role Cannot Be Empty")
    @Pattern(regexp = "^(Admin|Customer)$",message = "The User Role Must Be Admin or Customer")
    @Column(columnDefinition = "varchar(8) not null")
    private String role;
    @NotNull(message = "The User Balance Cannot Be Null")
    @Positive(message = "The Balance Must Be a Positive Value")
    @Column(columnDefinition = "int not null")
    private Integer balance;


}

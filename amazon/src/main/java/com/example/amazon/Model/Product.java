package com.example.amazon.Model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message="The Product Name Cannot Be Empty")
    @Size(min=3,message="The Product Name Must Be At Least 3 Length")
    private String name;
    @Positive(message = "The Product Price Must Be Positive Value")
    @Column(columnDefinition = "int not null")
    private Integer price;
    @Column(columnDefinition = "int not null")
    private Integer categoryID;
}

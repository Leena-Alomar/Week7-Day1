package com.example.amazon.Model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Merchantstock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "int not null")
    private Integer productID;
    @Column(columnDefinition = "int not null")
    private Integer merchantID;
    @NotNull(message="The Stock Cannot Be Null")
    @Min(value = 10, message = "The Stock Must Be at Least a Value of 10")
    @Column(columnDefinition = "int not null")
    private Integer stock;
}

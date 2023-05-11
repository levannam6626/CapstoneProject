package com.finalpbl.Model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Products {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long product_id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id")
    Category category;

    @NotNull
    private String product_name;

    @NotNull
    private String product_description;

    @NotNull
    private String product_image;

    @NotNull
    private LocalDate update_date;

    @NotNull
    private double product_price;
}



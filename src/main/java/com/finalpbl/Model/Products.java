package com.finalpbl.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
    @Column(name = "product_id")
    private long productId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id")
    Category category;

    @Column(name = "product_name")
    @NotNull
    private String productName;

    @Column(name = "product_description")
    @NotNull
    private String productDescription;

    @Column(name = "product_image")
    @NotNull
    private String productImage;

    @Column(name = "update_date")
    @NotNull
    private LocalDateTime updateDate;

    @Column(name = "product_price")
    @NotNull
    private double productPrice;
}



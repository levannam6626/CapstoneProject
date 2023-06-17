package com.finalpbl.Dto.Product;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private long productId;

    @NotBlank
    private long categoryId;

    @NotBlank
    private String productName;

    @NotBlank
    private String productDescription;

    @NotBlank
    private String productImage;

    @NotBlank
    private LocalDateTime updateDate;
    
    @NotBlank
    private double productPrice;

    @NotBlank
    private int quantity;
}

package com.finalpbl.Dto.Cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddEditCartDto {
    
    private long id;

    private int quantity;

    private long productID;

    private boolean selected;
}

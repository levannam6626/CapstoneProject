package com.finalpbl.Dto.Payment;

import java.io.Serializable;

import lombok.Data;

@Data
public class PaymentResponse implements Serializable{
    private String status;
    private String message;
    private String url;
}

package com.finalpbl.Service.Payment;

import java.io.UnsupportedEncodingException;

import com.finalpbl.Dto.Payment.PaymentResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface IPaymentService {
    public PaymentResponse createPayment(HttpServletRequest request, long totalCost, String email) throws UnsupportedEncodingException;
}

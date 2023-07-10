package com.finalpbl.Controller;

import java.io.UnsupportedEncodingException;
import java.io.ObjectInputFilter.Config;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finalpbl.Config.UserDetailsImpl;
import com.finalpbl.Config.VnPayConfig;
import com.finalpbl.Dto.Payment.PaymentResponse;
import com.finalpbl.Service.Payment.IPaymentService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin
public class PaymentController {
    @Autowired 
    private IPaymentService paymentService;
    @GetMapping("/create-payment")
    public ResponseEntity<?> createPayment(HttpServletRequest request, @RequestParam long TotalCost, @AuthenticationPrincipal UserDetailsImpl user) throws UnsupportedEncodingException{
        PaymentResponse paymentResponse = paymentService.createPayment(request, TotalCost, user.getUsername());
        return ResponseEntity.ok().body(paymentResponse);
    } 
}

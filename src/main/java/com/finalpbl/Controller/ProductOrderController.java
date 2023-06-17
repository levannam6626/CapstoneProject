package com.finalpbl.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalpbl.Config.UserDetailsImpl;
import com.finalpbl.Dto.Order.ProductOrderDto;
import com.finalpbl.Model.ProductOrder;
import com.finalpbl.Service.ProductOrder.IProductOrderService;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin
public class ProductOrderController {
    
    @Autowired
    private IProductOrderService iProductOrderService;

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllOrder()
    {
        List<ProductOrder> orders = iProductOrderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getOrderByID(long ID)
    {
        ProductOrder order = iProductOrderService.getOrderByID(ID);
        return ResponseEntity.ok(order);
    }

    @PostMapping(path = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createOrder(@ModelAttribute ProductOrderDto order, @AuthenticationPrincipal UserDetailsImpl user)
    {
        String msg = iProductOrderService.addOrder(order, user.getUsername());
        if(msg.equals("Add Order Success"))
        {
            return ResponseEntity.ok().body(msg);
        }
        return ResponseEntity.badRequest().body(msg);
    }
}

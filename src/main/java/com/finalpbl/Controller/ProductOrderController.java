package com.finalpbl.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalpbl.Config.UserDetailsImpl;
import com.finalpbl.Dto.Order.OrderUpdateRequestDto;
import com.finalpbl.Dto.Order.ProductOrderDto;
import com.finalpbl.Model.ProductOrder;
import com.finalpbl.Service.ProductOrder.IProductOrderService;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin
public class ProductOrderController {
    
    @Autowired
    private IProductOrderService iProductOrderService;

    @GetMapping(path = "view/all")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<?> getAllOrder()
    {
        List<ProductOrderDto> orders = iProductOrderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping(path = "view/{id}")
    public ResponseEntity<?> getOrderByID(long ID)
    {
        ProductOrder order = iProductOrderService.getOrderByID(ID);
        return ResponseEntity.ok(order);
    }

    @GetMapping(path = "view/getByUser")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> getOrderByUser(@AuthenticationPrincipal UserDetailsImpl user)
    {
        List<ProductOrderDto> orders = iProductOrderService.getOrdersByUser(user.getUsername());
        return ResponseEntity.ok(orders);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> createOrder(@ModelAttribute ProductOrderDto productOrderDto,@AuthenticationPrincipal UserDetailsImpl user)
    {
        String msg = iProductOrderService.PlaceOrder(productOrderDto, user.getUsername());
        if(msg.equals("Add Order Success"))
        {
            return ResponseEntity.ok().body(msg);
        }
        return ResponseEntity.badRequest().body(msg);
    }

    @PostMapping(path = "/edit")
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<?> editOrder(@ModelAttribute OrderUpdateRequestDto order)
    {
        String msg = iProductOrderService.editOrder(order);
        if(msg.equals("Edit order success"))
        {
            return ResponseEntity.ok().body(msg);
        }
        return ResponseEntity.badRequest().body(msg);
    }
}

package com.finalpbl.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalpbl.Config.UserDetailsImpl;
import com.finalpbl.Dto.Cart.AddEditCartDto;
import com.finalpbl.Dto.Cart.CartDto;
import com.finalpbl.Dto.Cart.CartItemDto;
import com.finalpbl.Service.Cart.ICartService;

@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin
public class CartController {
    
    @Autowired
    private ICartService cartService;

    @PostMapping(path = "/addCart")
    public ResponseEntity<?> AddCart(@ModelAttribute AddEditCartDto cartItemDto, @AuthenticationPrincipal UserDetailsImpl user)
    {
        String msg = cartService.AddCartItem(cartItemDto, user.getUsername());
        if(msg.equals("Add Success"))
        {
            return ResponseEntity.ok().body(msg);
        }
        return ResponseEntity.badRequest().body("Cannot Add Cart Item");
    }

    @GetMapping(path = "/getCart")
    public ResponseEntity<?> GetCart(@AuthenticationPrincipal UserDetailsImpl user)
    {
        CartDto cartDto = cartService.findByUserOrderByCreatedDateDesc(user.getUsername());
        return ResponseEntity.ok(cartDto);
    }   

    @PostMapping(path = "/updateCart")
    public ResponseEntity<?> updateCart(@ModelAttribute AddEditCartDto cartItemDto)
    {
        String msg = cartService.UpdateCart(cartItemDto);
        if(msg.equals("Edit Success"))
        {
            return ResponseEntity.ok().body(msg);
        }
        return ResponseEntity.badRequest().body("Cannot Edit Cart Item");
    }

    @DeleteMapping(path = "/deleteCart/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable(name = "id") long ID)
    {
        String msg = cartService.DeleteCart(ID);
        if(msg.equals("Delete Success"))
        {
            return ResponseEntity.ok().body(msg);
        }
        return ResponseEntity.badRequest().body(msg);
    }

}

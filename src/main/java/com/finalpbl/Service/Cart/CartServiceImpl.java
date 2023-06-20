package com.finalpbl.Service.Cart;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalpbl.Dto.Cart.AddEditCartDto;
import com.finalpbl.Dto.Cart.CartDto;
import com.finalpbl.Dto.Cart.CartItemDto;
import com.finalpbl.Dto.Product.ProductDto;
import com.finalpbl.Model.Cart;
import com.finalpbl.Model.Products;
import com.finalpbl.Model.User;
import com.finalpbl.Repository.CartRepository;
import com.finalpbl.Repository.ProductsRepository;
import com.finalpbl.Repository.UserRepository;

@Service
public class CartServiceImpl implements ICartService{
    
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private ProductsRepository productsRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CartDto findByUserOrderByCreatedDateDesc(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        List<Cart> carts = cartRepository.findByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        for(Cart cart:carts)
        {
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setId(cart.getId());
            cartItemDto.setProducts(cart.getProducts());
            cartItemDto.setQuantity(cart.getQty());
            cartItemDto.setSelected(cart.getSelected());
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for(CartItemDto cartItem:cartItems)
        {
            totalCost += (cartItem.getProducts().getProductPrice() * cartItem.getQuantity());
        }
        return new CartDto(cartItems, totalCost);
    }

    @Override
    public String AddCartItem(AddEditCartDto cartItemDto, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Products products = productsRepository.findById(cartItemDto.getProductID()).orElseThrow();
        Cart cart = cartRepository.findByProductsandUser(products.getProductId(), user.getId()).orElse(null);
        if(cart == null)
        {
            cart = new Cart();
            cart.setProducts(products);
            cart.setQty(cartItemDto.getQuantity());
            cart.setUser(user);
            cart.setSelected(false);
            cart.setCreatedDate(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
            cartRepository.save(cart);
            return "Add Success";
        }
        else
        {
            if((cart.getQty() + cartItemDto.getQuantity()) <= products.getQuantity())
            {
                cart.setQty(cart.getQty() + cartItemDto.getQuantity());
                cartRepository.save(cart);
                return "Add Success";
            }
            return "Quantity more than allow";
        }
    }

    @Override
    public String UpdateCart(AddEditCartDto cartItemDto) {
        Cart cart = cartRepository.findById(cartItemDto.getId()).orElseThrow();
        cart.setQty(cartItemDto.getQuantity());
        cart.setSelected(cartItemDto.isSelected());
        cartRepository.save(cart);
        return "Edit Success";
    }

    @Override
    public String DeleteCart(long CartId) {
        Cart cart = cartRepository.findById(CartId).orElseThrow(null);
        if(cart != null)
        {
            cartRepository.delete(cart);
            return "Delete Success";
        }
        return "Cart not exist";
    }

    @Override
    public String DeleteCartByUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(null);
        cartRepository.deleteByUser(user);
        return "Delete Success";
    }

    @Override
    public String DeleteOrderdItem(String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow(null);
        cartRepository.deleteBySelectedAndUser(user.getId());
        return "Delete Success";
    }

    
}

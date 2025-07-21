package com.example.dream_shops.service.cart;

import com.example.dream_shops.exception.ResourceNotFoundExceotion;
import com.example.dream_shops.model.Cart;
import com.example.dream_shops.model.CartItem;
import com.example.dream_shops.model.Product;
import com.example.dream_shops.repository.CartItemRepository;
import com.example.dream_shops.repository.CartRepository;
import com.example.dream_shops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;


    @Override
    public void addItemToCart(Long cartId, Long ProductId, int quantity) {
        //1.Get the cart
        //2.Get the product
        //3.check if the product already in the cart
        //4.if yes,then increase the quantity with the requested quantity
        //5.if no,the initiate a new CartItem entry.
        Cart cart=cartService.getCart(cartId);
        Product product=productService.getProductById(ProductId);
        CartItem cartItem=cart.getItems()
                .stream()
                .filter(item->item.getProduct().getId().equals(ProductId))
                .findFirst()
                .orElse(new CartItem());
        if (cartItem.getId()==null){
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
            cartItem.setProduct(product);
            cartItem.setUnitPrice(product.getPrice());
        }
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
          cartItem.setTotalPrice();
          cart.addItem(cartItem);
          cartItemRepository.save(cartItem);
          cartRepository.save(cart);
        }



    @Override
    public void removeItemFromCart(Long cartId, Long ProductId) {
        Cart cart=cartService.getCart(cartId);
        CartItem itemToRemove=getCartItem(cartId,ProductId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getItems()
                .stream().map(CartItem ::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long ProductId) {
        Cart cart=cartService.getCart(cartId);
              return   cart.getItems()
                .stream()
                .filter(item->item.getProduct().getId().equals(ProductId))
                .findFirst()
                .orElseThrow(()->  new ResourceNotFoundExceotion("item not found"));

    }
}

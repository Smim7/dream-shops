package com.example.dream_shops.service.order;

import com.example.dream_shops.enums.OrderStatus;
import com.example.dream_shops.exception.ResourceNotFoundExceotion;
import com.example.dream_shops.model.Cart;
import com.example.dream_shops.model.Order;
import com.example.dream_shops.model.OrderItem;
import com.example.dream_shops.model.Product;
import com.example.dream_shops.repository.OrderRepository;
import com.example.dream_shops.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    @Override
    public Order placeOrder(Long userId) {
        return null;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        //set the user...
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(()->  new ResourceNotFoundExceotion("Order not found")).getOrder();
    }

    private List<OrderItem> vreateOrderIteams(Order order, Cart cart) {
        return cart.getItems().stream().map(cartItem -> {
            Product product=cartItem.getProduct();
            product.setInventory(product.getInventory()-cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());}).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemsList) {
        return  orderItemsList
                .stream()
                .map(item->item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}



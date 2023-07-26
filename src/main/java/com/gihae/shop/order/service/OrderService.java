package com.gihae.shop.order.service;

import com.gihae.shop._core.errors.exception.Exception400;
import com.gihae.shop._core.errors.exception.Exception404;
import com.gihae.shop.cart.repository.Cart;
import com.gihae.shop.cart.repository.CartJPARepository;
import com.gihae.shop.order.controller.dto.OrderResponse;
import com.gihae.shop.order.repository.Order;
import com.gihae.shop.order.repository.OrderJPARepository;
import com.gihae.shop.orderitem.repository.OrderItem;
import com.gihae.shop.orderitem.repository.OrderItemJPARepository;
import com.gihae.shop.user.repository.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final OrderItemJPARepository orderItemJPARepository;
    private final CartJPARepository cartJPARepository;

    @Transactional
    public OrderResponse.FindByIdDTO save(User user){
        List<Cart> carts = cartJPARepository.findByUserId(user.getId());

        //1. 사용자 장바구니가 비어있을 경우
        if(carts.isEmpty()){
            throw new Exception404("사용자의 장바구니가 비어있습니다.");
        }

        Order order = Order.builder().user(user).build();
        orderJPARepository.save(order);
        for (Cart cart : carts) {
            OrderItem orderItem = OrderItem.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getPrice())
                    .build();
            orderItemJPARepository.save(orderItem);
        }

        Order findOrder = orderJPARepository.findById(order.getId()).orElseThrow(
                () -> new Exception404("주문을 찾을 수 없습니다. : " + order.getId())
        );

        List<OrderItem> orderItems = orderItemJPARepository.findByOrderId(findOrder.getId());
        cartJPARepository.deleteAll();

        return new OrderResponse.FindByIdDTO(order, orderItems);
    }

    public OrderResponse.FindByIdDTO findById(Long id){
        Order order = orderJPARepository.findById(id).orElseThrow(
                () -> new Exception400("주문을 찾을 수 없습니다. : " + id)
        );
        List<OrderItem> orderItems = orderItemJPARepository.findByOrderId(order.getId());
        return new OrderResponse.FindByIdDTO(order, orderItems);
    }
}
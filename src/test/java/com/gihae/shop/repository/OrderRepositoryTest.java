package com.gihae.shop.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gihae.shop._core.util.DummyEntity;
import com.gihae.shop.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @BeforeEach
    public void setUp(){
        User userA = newUser("userA");
        User userB = newUser("userB");
        userRepository.saveAll(Arrays.asList(userA, userB));

        List<Product> products = productDummyList();
        productRepository.saveAll(products);

        List<Option> options = optionDummyList(products);
        optionRepository.saveAll(options);

        List<Cart> cartsA = cartDummyList(userA, options, Arrays.asList(1, 2));
        List<Cart> cartsB = cartDummyList(userB, options, Arrays.asList(6, 7));
        cartRepository.saveAll(cartsA);
        cartRepository.saveAll(cartsB);

        Order orderA = newOrder(userA);
        Order orderB = newOrder(userB);
        orderRepository.saveAll(Arrays.asList(orderA, orderB));

        orderItemRepository.saveAll(itemDummyList(cartsA, orderA));
        orderItemRepository.saveAll(itemDummyList(cartsB, orderB));

        em.clear();
    }

    @Test
    @DisplayName("전체 주문 내역 조회")
    void order_findAll_test(){
        //given

        //when
        List<Order> findAllList = orderRepository.findAll();

        //then
        assertThat(findAllList).hasSize(2);
    }

    @Test
    @DisplayName("주문 번호로 주문 아이템 조회 성공")
    void item_findByOrderId_test() {
        //given
        Long orderId = 2L;

        //when
        List<OrderItem> findByIdList = orderItemRepository.findByOrderId(orderId);

        //then
        assertThat(findByIdList).hasSize(2);
        assertThat(findByIdList.get(0).getId()).isEqualTo(3); //itemId
        assertThat(findByIdList.get(0).getOrder().getId()).isEqualTo(2); //orderId
        assertThat(findByIdList.get(0).getOrder().getUser().getId()).isEqualTo(2); //userId
        assertThat(findByIdList.get(0).getOption().getId()).isEqualTo(6); //optionId
        assertThat(findByIdList.get(0).getOption().getProduct().getId()).isEqualTo(2); //productId
        assertThat(findByIdList.get(0).getPrice()).isEqualTo(9900);
    }

    @Test
    @DisplayName("주문 번호로 주문 아이템 조회 실패")
    void item_findByOrderId_fail_test() {
        //given
        Long orderId = 100L;

        //when
        List<OrderItem> findByIdList = orderItemRepository.findByOrderId(orderId);

        //then
        assertThat(findByIdList).isEmpty();
    }

    @Test
    @DisplayName("주문 번호로 주문 아이템 조회 - fetch join")
    void item_findByOrderId_joinFetch_test() {
        //given
        Long orderId = 1L;

        //when
        List<OrderItem> findByOrderIdList = orderItemRepository.findByOrderId(orderId);
        //List<Item> findByOrderIdList = orderItemRepository.findByOrderId(orderId);

        //then
        assertThat(findByOrderIdList).hasSize(2);
        assertThat(findByOrderIdList.get(0).getId()).isEqualTo(1); //itemId
        assertThat(findByOrderIdList.get(0).getOrder().getId()).isEqualTo(1); //orderId
        assertThat(findByOrderIdList.get(0).getOrder().getUser().getId()).isEqualTo(1); //userId
        assertThat(findByOrderIdList.get(0).getOption().getId()).isEqualTo(1); //optionId
        assertThat(findByOrderIdList.get(0).getOption().getProduct().getId()).isEqualTo(1); //productId
        assertThat(findByOrderIdList.get(0).getPrice()).isEqualTo(10000);
    }
}
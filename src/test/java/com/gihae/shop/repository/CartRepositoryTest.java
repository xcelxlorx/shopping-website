package com.gihae.shop.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.gihae.shop._core.util.DummyEntity;
import com.gihae.shop.controller.dto.response.CartResponse;
import com.gihae.shop.domain.Cart;
import com.gihae.shop.domain.Option;
import com.gihae.shop.domain.Product;
import com.gihae.shop.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(ObjectMapper.class)
@DataJpaTest
class CartRepositoryTest extends DummyEntity {

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
    private ObjectMapper om;

    @BeforeEach
    void setUp(){
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

        em.clear();
    }

    @Test
    @DisplayName("장바구니 전체 조회")
    void cart_findAll_test() {
        //given

        //when
        List<Cart> findAllList = cartRepository.findAll();

        //then
        assertThat(findAllList).hasSize(4);
        assertThat(findAllList.get(0).getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getPrice()).isEqualTo(10000);
        assertThat(findAllList.get(0).getQuantity()).isEqualTo(1);
        assertThat(findAllList.get(0).getOption().getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getOption().getProduct().getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getUser().getId()).isEqualTo(1); //userA
        assertThat(findAllList.get(3).getId()).isEqualTo(4);
        assertThat(findAllList.get(3).getPrice()).isEqualTo(14500);
        assertThat(findAllList.get(3).getQuantity()).isEqualTo(1);
        assertThat(findAllList.get(3).getOption().getId()).isEqualTo(7);
        assertThat(findAllList.get(3).getUser().getId()).isEqualTo(2); //userB
        assertThat(findAllList.get(3).getOption().getProduct().getId()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 전체 조회 - fetch join")
    void cart_findAll_joinFetch_test() {
        //given

        //when
        List<Cart> findAllList = cartRepository.findAll();

        //then
        assertThat(findAllList).hasSize(4);
        assertThat(findAllList.get(0).getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getPrice()).isEqualTo(10000);
        assertThat(findAllList.get(0).getQuantity()).isEqualTo(1);
        assertThat(findAllList.get(0).getOption().getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getOption().getProduct().getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getUser().getId()).isEqualTo(1); //userA
        assertThat(findAllList.get(3).getId()).isEqualTo(4);
        assertThat(findAllList.get(3).getPrice()).isEqualTo(14500);
        assertThat(findAllList.get(3).getQuantity()).isEqualTo(1);
        assertThat(findAllList.get(3).getOption().getId()).isEqualTo(7);
        assertThat(findAllList.get(3).getUser().getId()).isEqualTo(2); //userB
        assertThat(findAllList.get(3).getOption().getProduct().getId()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 전체 조회 - 메시지 컨버터 실패")
    void cart_findAll_messageConverter_fail_test() {
        //given

        //when
        List<Cart> findAllList = cartRepository.findAll();
        System.out.println("========== JSON 직렬화 ==========");
        InvalidDefinitionException exception = assertThrows(InvalidDefinitionException.class, () -> om.writeValueAsString(findAllList));

        //then
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).contains("No serializer found");
    }

    @Test
    @DisplayName("장바구니 전체 조회 - 메시지 컨버터 dto")
    void cart_findAll_messageConverter_dto_test() throws JsonProcessingException {
        //given

        //when
        List<Cart> findAllList = cartRepository.findAll();
        System.out.println("========== JSON 직렬화 ==========");
        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(findAllList);
        String responseBody = om.writeValueAsString(responseDTO);
        System.out.println("responseBody=" + responseBody);

        //then
        assertThat(findAllList).hasSize(4);
        assertThat(findAllList.get(0).getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getPrice()).isEqualTo(10000);
        assertThat(findAllList.get(0).getQuantity()).isEqualTo(1);
        assertThat(findAllList.get(0).getOption().getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getOption().getProduct().getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getUser().getId()).isEqualTo(1); //userA
        assertThat(findAllList.get(3).getId()).isEqualTo(4);
        assertThat(findAllList.get(3).getPrice()).isEqualTo(14500);
        assertThat(findAllList.get(3).getQuantity()).isEqualTo(1);
        assertThat(findAllList.get(3).getOption().getId()).isEqualTo(7);
        assertThat(findAllList.get(3).getUser().getId()).isEqualTo(2); //userB
        assertThat(findAllList.get(3).getOption().getProduct().getId()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 전체 조회 - 메시지 컨버터 fetch join")
    void cart_findAll_messageConverter_joinFetch_test() throws JsonProcessingException {
        //given

        //when
        List<Cart> findAllList = cartRepository.findAll();
        System.out.println("========== JSON 직렬화 ==========");
        CartResponse.FindAllDTO responseDTO = new CartResponse.FindAllDTO(findAllList);
        String responseBody = om.writeValueAsString(responseDTO);
        System.out.println("responseBody=" + responseBody);

        //then
        assertThat(findAllList).hasSize(4);
        assertThat(findAllList.get(0).getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getPrice()).isEqualTo(10000);
        assertThat(findAllList.get(0).getQuantity()).isEqualTo(1);
        assertThat(findAllList.get(0).getOption().getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getOption().getProduct().getId()).isEqualTo(1);
        assertThat(findAllList.get(0).getUser().getId()).isEqualTo(1); //userA
        assertThat(findAllList.get(3).getId()).isEqualTo(4);
        assertThat(findAllList.get(3).getPrice()).isEqualTo(14500);
        assertThat(findAllList.get(3).getQuantity()).isEqualTo(1);
        assertThat(findAllList.get(3).getOption().getId()).isEqualTo(7);
        assertThat(findAllList.get(3).getUser().getId()).isEqualTo(2); //userB
        assertThat(findAllList.get(3).getOption().getProduct().getId()).isEqualTo(2);

    }

    @Test
    @DisplayName("장바구니 아이디로 조회 성공")
    void cart_findById_test() {
        //given
        Long id = 1L;

        //when
        Cart cart = cartRepository.findById(id).get();

        //then
        assertThat(cart.getId()).isEqualTo(1);
        assertThat(cart.getPrice()).isEqualTo(10000);
        assertThat(cart.getQuantity()).isEqualTo(1);
        assertThat(cart.getOption().getId()).isEqualTo(1);
        assertThat(cart.getUser().getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("장바구니 아이디로 조회 실패")
    void cart_findById_fail_test() {
        //given
        Long id = 5L;

        //when
        Optional<Cart> cart = cartRepository.findById(id);

        //then
        assertThat(cart).isEmpty();
    }

    @Test
    @DisplayName("사용자의 장바구니 조회 성공")
    void cart_findByUserId_test() {
        //given
        Long userId = 1L;

        //when
        List<Cart> findByUserIdList = cartRepository.findByUserId(userId);

        //then
        assertThat(findByUserIdList).hasSize(2);
        assertThat(findByUserIdList.get(0).getId()).isEqualTo(1);
        assertThat(findByUserIdList.get(0).getPrice()).isEqualTo(10000);
        assertThat(findByUserIdList.get(0).getQuantity()).isEqualTo(1);
        assertThat(findByUserIdList.get(0).getOption().getId()).isEqualTo(1);
        assertThat(findByUserIdList.get(0).getUser().getId()).isEqualTo(1);
        assertThat(findByUserIdList.get(1).getId()).isEqualTo(2);
        assertThat(findByUserIdList.get(1).getPrice()).isEqualTo(10900);
        assertThat(findByUserIdList.get(1).getQuantity()).isEqualTo(1);
        assertThat(findByUserIdList.get(1).getOption().getId()).isEqualTo(2);
        assertThat(findByUserIdList.get(1).getUser().getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("사용자의 장바구니 조회 실패")
    void cart_findByUserId_fail_test() {
        //given
        Long userId = 3L;

        //when
        List<Cart> findByUserIdList = cartRepository.findByUserId(userId);

        //then
        assertThat(findByUserIdList).isEmpty();
    }

    @Test
    @DisplayName("장바구니 수정 성공")
    void cart_update_test() {
        //given
        Long id = 1L;
        int quantity = 5;

        //when
        cartRepository.findById(id).ifPresent(
                cart -> {
                    cart.update(quantity, cart.getPrice() * quantity);
                    cartRepository.update(cart);
                    em.flush();
                });

        Cart updatedCart = cartRepository.findById(id).get();

        //then
        assertThat(updatedCart.getId()).isEqualTo(1);
        assertThat(updatedCart.getPrice()).isEqualTo(50000);
        assertThat(updatedCart.getQuantity()).isEqualTo(5);
        assertThat(updatedCart.getOption().getId()).isEqualTo(1);
        assertThat(updatedCart.getUser().getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("장바구니 수정 실패")
    void cart_update_fail_test() {
        //given
        Long id = 100L;
        int quantity = 5;

        //when
        cartRepository.findById(id).ifPresent(
                cart -> {
                    cart.update(quantity, cart.getPrice() * quantity);
                    cartRepository.update(cart);
                    em.flush();
                });

        Optional<Cart> updatedCart = cartRepository.findById(id);

        //then
        assertThat(updatedCart).isEmpty();
    }

    @Test
    @DisplayName("장바구니 전체 삭제")
    void cart_deleteAll_test(){
        //given

        // then
        cartRepository.deleteAll();
        List<Cart> findAllList = cartRepository.findAll();

        //then
        assertThat(findAllList).isEmpty();
    }

    @Test
    @DisplayName("장바구니 개별 삭제")
    void cart_deleteById_test(){
        //given
        Long id = 1L;

        // then
        Optional<Cart> cart = cartRepository.findById(id);
        cart.ifPresent(c -> {
            cartRepository.deleteById(id);
        });

        Optional<Cart> deletedCart = cartRepository.findById(id);

        //then
        assertThat(deletedCart).isEmpty();
    }
}
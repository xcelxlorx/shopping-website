package com.gihae.shop.cart.service;

import com.gihae.shop._core.exception.Exception400;
import com.gihae.shop._core.exception.Exception404;
import com.gihae.shop.cart.controller.dto.CartRequest;
import com.gihae.shop.cart.controller.dto.CartResponse;
import com.gihae.shop.cart.repository.Cart;
import com.gihae.shop.cart.repository.CartJPARepository;
import com.gihae.shop.option.repository.Option;
import com.gihae.shop.option.repository.OptionJPARepository;
import com.gihae.shop.user.repository.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CartService {
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;

    @Transactional
    public void addCartList(List<CartRequest.SaveDTO> requestDTOs, User sessionUser){

        //1. requestDTOs에 동일한 옵션 아이디가 존재할 경우
        Set<Long> optionIds = new HashSet<>();
        boolean isDuplicated = requestDTOs.stream()
                .map(CartRequest.SaveDTO::getOptionId)
                .anyMatch(optionId -> !optionIds.add(optionId));
        if(isDuplicated){
            throw new Exception400("입력 중 동일한 옵션 아이디가 존재합니다.");
        }

        //2. 카드가 존재할 경우 update, 존재하지 않을 경우 save
        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            Long optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            Optional<Cart> optionalCart = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());

            if(optionalCart.isPresent()){
                Cart cart = optionalCart.get();
                cart.update(quantity, cart.getOption().getPrice() * quantity);
                cartJPARepository.update(cart);
            }else{
                Option option = optionJPARepository.findById(optionId).orElseThrow(
                        () -> new Exception404("해당 옵션을 찾을 수 없습니다.")
                );
                int price = option.getPrice() * quantity;
                Cart cart = Cart.builder().user(sessionUser).option(option).quantity(quantity).price(price).build();
                cartJPARepository.save(cart);
            }
        }
    }

    public CartResponse.FindAllDTO findAll(User user){
        List<Cart> carts = cartJPARepository.findByUserId(user.getId());
        return new CartResponse.FindAllDTO(carts);
    }

    @Transactional
    public CartResponse.UpdateDTO update(List<CartRequest.UpdateDTO> requestDTOs, User user){
        List<Cart> carts = cartJPARepository.findByUserId(user.getId());

        //1. 사용자 장바구니가 비어있을 경우
        if(carts.isEmpty()){
            throw new Exception400("사용자의 장바구니가 비어있습니다.");
        }

        //2. requestDTOs에 동일한 장바구니 아이디가 존재할 경우
        Set<Long> cartIds = new HashSet<>();
        boolean isDuplicated = requestDTOs.stream()
                .map(CartRequest.UpdateDTO::getCardId)
                .anyMatch(cartId -> !cartIds.add(cartId));
        if(isDuplicated){
            throw new Exception400("입력 중 동일한 장바구니 아이디가 존재합니다.");
        }

        //3. 사용자 장바구니에 있는 cartId가 들어온 경우 update
        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            Long cartId = requestDTO.getCardId();
            int quantity = requestDTO.getQuantity();

            Cart cart = carts.stream().filter(c -> c.getId().equals(cartId)).findFirst().orElseThrow(
                    () -> new Exception404("해당 카트를 찾을 수 없습니다.")
            );
            cart.update(quantity, cart.getOption().getPrice() * quantity);
            cartJPARepository.update(cart);
        }

        return new CartResponse.UpdateDTO(carts);
    }
}

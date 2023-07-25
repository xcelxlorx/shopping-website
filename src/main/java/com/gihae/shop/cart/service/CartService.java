package com.gihae.shop.cart.service;

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

        Set<Long> optionIds = new HashSet<>();
        boolean isDuplicated = requestDTOs.stream()
                .map(CartRequest.SaveDTO::getOptionId)
                .anyMatch(optionId -> !optionIds.add(optionId));
        if(isDuplicated){
            //예외 처리
        }

        for (CartRequest.SaveDTO requestDTO : requestDTOs) {
            Long optionId = requestDTO.getOptionId();
            int quantity = requestDTO.getQuantity();
            Optional<Cart> optionalCart = cartJPARepository.findByOptionIdAndUserId(optionId, sessionUser.getId());

            if(optionalCart.isPresent()){
                Cart cart = optionalCart.get();
                cart.update(quantity, cart.getOption().getPrice() * quantity);
                cartJPARepository.update(cart);
            }else{
                Option option = optionJPARepository.findById(optionId).orElseThrow();
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

        if(carts.isEmpty()){
            //예외처리
        }

        Set<Long> cartIds = new HashSet<>();
        boolean isDuplicated = requestDTOs.stream()
                .map(CartRequest.UpdateDTO::getCardId)
                .anyMatch(cartId -> !cartIds.add(cartId));
        if(isDuplicated){
            //예외 처리
        }

        for (CartRequest.UpdateDTO requestDTO : requestDTOs) {
            Long cartId = requestDTO.getCardId();
            int quantity = requestDTO.getQuantity();

            Cart cart = carts.stream().filter(c -> c.getId().equals(cartId)).findFirst().orElseThrow();
            cart.update(quantity, cart.getOption().getPrice() * quantity);
            cartJPARepository.update(cart);
        }

        return new CartResponse.UpdateDTO(carts);
    }
}

package com.tirestore.tireshop.service;

import com.tirestore.tireshop.dto.request.AddToCartRequest;
import com.tirestore.tireshop.entity.Account;
import com.tirestore.tireshop.entity.CartItem;
import com.tirestore.tireshop.entity.Item;
import com.tirestore.tireshop.entity.id.CartItemId;
import com.tirestore.tireshop.repository.AccountRepository;
import com.tirestore.tireshop.repository.CartItemRepository;
import com.tirestore.tireshop.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final AccountRepository accountRepository;

    public CartService(CartItemRepository cartItemRepository, ItemRepository itemRepository, AccountRepository accountRepository) {
        this.cartItemRepository = cartItemRepository;
        this.itemRepository = itemRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void addToCart(AddToCartRequest addToCartRequest, int userId) {
        CartItemId cartItemId = new CartItemId(userId,addToCartRequest.itemId());

        cartItemRepository.findById(cartItemId).ifPresentOrElse(
                existing -> existing.setQuantity(existing.getQuantity() + addToCartRequest.quantity()),
                () -> {
                    Item item = itemRepository.findById(addToCartRequest.itemId()).orElseThrow(() -> new EntityNotFoundException("Item not found"));

                    Account account = accountRepository.getReferenceById(userId);

                    CartItem cartItem = new CartItem();
                    cartItem.setItem(item);
                    cartItem.setAccount(account);
                    cartItem.setQuantity(addToCartRequest.quantity());
                    cartItemRepository.save(cartItem);
                }
        );
    }

    @Transactional
    public void removeFromCart(int userId, int itemId) {
        CartItemId cartItemId = new CartItemId(userId,itemId);

        cartItemRepository.findById(cartItemId).ifPresentOrElse(
                cartItemRepository::delete,
                () -> {
                    throw new EntityNotFoundException("Item not found");
                }
        );
    }

    @Transactional
    public void changeQuantity(int userId, int itemId, int quantity) {
        CartItemId cartItemId = new CartItemId(userId,itemId);

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new EntityNotFoundException("Item not found"));
        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);
    }
}

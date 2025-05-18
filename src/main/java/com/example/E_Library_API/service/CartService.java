package com.example.E_Library_API.service;

import com.example.E_Library_API.dao.entity.Books;
import com.example.E_Library_API.dao.entity.Cart;
import com.example.E_Library_API.dao.entity.Users;
import com.example.E_Library_API.dao.repository.mongo.BooksRepository;
import com.example.E_Library_API.dao.repository.mongo.CartRepository;
import com.example.E_Library_API.dto.response.BooksResponse;
import com.example.E_Library_API.dto.response.pagination.BooksPaginationResponse;
import com.example.E_Library_API.exception.EntityNotFoundException;
import com.example.E_Library_API.mapper.BooksMapper;
import com.example.E_Library_API.security.AuthHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final BooksRepository booksRepository;
    private final BooksMapper booksMapper;
    private final AuthHelperService authHelperService;

    public BooksPaginationResponse getBookFromCart(String currentUserEmail) {
        Users users = authHelperService.getAuthenticatedUser(currentUserEmail);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Cart> allBooksFromCart = cartRepository.findByUserId(users.getId(), pageable);

        List<BooksResponse> booksResponseList = allBooksFromCart.getContent()
                .stream()
                .flatMap(cart -> {
                    List<Books> booksList = booksRepository.findAllById(cart.getBookId());
                    if (booksList.isEmpty()) {
                        throw new EntityNotFoundException("No books found for cart ID: " + cart.getId());
                    }
                    return booksList.stream().map(booksMapper::toBooksResponse);
                })
                .collect(Collectors.toList());

        return new BooksPaginationResponse(
                booksResponseList,
                allBooksFromCart.getTotalElements(),
                allBooksFromCart.getTotalPages(),
                allBooksFromCart.hasNext());
    }

    public void addBookToCart(String id, String currentUserEmail) {
        Users users = authHelperService.getAuthenticatedUser(currentUserEmail);
        Cart cart = cartRepository.findByUserId(users.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        if (booksRepository.existsById(id)) {
            if (cart.getBookId() == null) {
                cart.setBookId(new ArrayList<>());
            }
            cart.getBookId().add(id);
        } else {
            throw new EntityNotFoundException("Book not found");
        }
        cartRepository.save(cart);
    }

    public void deleteBookFromCart(String id, String currentUserEmail) {
        Users users = authHelperService.getAuthenticatedUser(currentUserEmail);
        Cart cart = cartRepository.findByUserId(users.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        if (cart.getBookId() != null) {
            cart.getBookId().remove(id);
        }
        cartRepository.save(cart);
    }
}

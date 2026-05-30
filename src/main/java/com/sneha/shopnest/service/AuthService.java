package com.sneha.shopnest.service;

import com.sneha.shopnest.dto.request.LoginRequest;
import com.sneha.shopnest.dto.request.RegisterRequest;
import com.sneha.shopnest.dto.response.AuthResponse;
import com.sneha.shopnest.entity.Cart;
import com.sneha.shopnest.entity.User;
import com.sneha.shopnest.enums.Role;
import com.sneha.shopnest.repository.CartRepository;
import com.sneha.shopnest.repository.UserRepository;
import com.sneha.shopnest.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, CartRepository cartRepository,
                       PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        System.out.println("=== REGISTER ===");
        System.out.println("Email: " + request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_CUSTOMER);

        User saved = userRepository.save(user);
        System.out.println("User saved with ID: " + saved.getId());

        Cart cart = new Cart();
        cart.setUser(saved);
        cartRepository.save(cart);
        System.out.println("Cart created");

        String token = jwtService.generateToken(saved.getEmail());
        System.out.println("Token generated");

        return new AuthResponse(token, saved.getEmail(), saved.getFullName(), saved.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        System.out.println("=== LOGIN ===");
        System.out.println("Email: " + request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("User found. Checking password...");

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            System.out.println("Password mismatch!");
            throw new RuntimeException("Invalid email or password");
        }

        System.out.println("Password matches!");

        String token = jwtService.generateToken(user.getEmail());
        System.out.println("Token: " + token);

        return new AuthResponse(token, user.getEmail(), user.getFullName(), user.getRole().name());
    }
}
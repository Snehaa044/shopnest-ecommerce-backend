package com.sneha.shopnest.service;

import com.sneha.shopnest.dto.response.DashboardResponse;
import com.sneha.shopnest.enums.OrderStatus;
import com.sneha.shopnest.enums.Role;
import com.sneha.shopnest.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    public DashboardService(OrderRepository orderRepository,
                            ProductRepository productRepository,
                            UserRepository userRepository,
                            OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public DashboardResponse getAdminDashboard() {
        DashboardResponse response = new DashboardResponse();
        response.setTotalOrders(orderRepository.count());
        response.setPendingOrders(orderRepository.countByStatus(OrderStatus.PENDING));
        response.setTotalProducts(productRepository.count());
        response.setTotalCustomers(userRepository.countByRole(Role.ROLE_CUSTOMER));
        response.setTotalRevenue(orderRepository.totalRevenue());

        List<DashboardResponse.TopProduct> topProducts = new ArrayList<>();
        orderItemRepository.findTopProducts().stream().limit(5).forEach(row -> {
            DashboardResponse.TopProduct tp = new DashboardResponse.TopProduct();
            tp.setProductName(row[0].toString());
            tp.setTotalSold(((Number) row[1]).longValue());
            topProducts.add(tp);
        });
        response.setTopProducts(topProducts);
        return response;
    }
}
package com.sneha.shopnest.repository;

import com.sneha.shopnest.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT oi.product.name, SUM(oi.quantity) FROM OrderItem oi " +
            "GROUP BY oi.product.name ORDER BY SUM(oi.quantity) DESC")
    List<Object[]> findTopProducts();
}
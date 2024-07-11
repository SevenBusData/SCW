package com.seven.repository;

/**
 * @author: seven
 * @since: 2024/7/11 15:29
 */
import com.seven.entity.TOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<TOrder, Integer> {
}



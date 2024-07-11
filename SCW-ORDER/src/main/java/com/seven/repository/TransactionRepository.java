package com.seven.repository;

/**
 * @author: seven
 * @since: 2024/7/11 15:29
 */

import com.seven.entity.TTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TTransaction, Integer> {
}
package com.seven.repository;

/**
 * @author: seven
 * @since: 2024/7/11 13:29
 */
import com.seven.entity.TReturn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReturnRepository extends JpaRepository<TReturn, Integer> {
    public List<TReturn> findByProjectid(Integer projectId);
}

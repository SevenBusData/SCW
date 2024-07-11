package com.seven.repository;

/**
 * @author: seven
 * @since: 2024/7/11 13:31
 */
import com.seven.entity.TProjectTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTagRepository extends JpaRepository<TProjectTag, Integer> {
}


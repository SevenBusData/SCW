package com.seven.repository;

/**
 * @author: seven
 * @since: 2024/7/11 13:28
 */
import com.seven.entity.TProjectType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTypeRepository extends JpaRepository<TProjectType, Integer> {
}
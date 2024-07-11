package com.seven.repository;

/**
 * @author: seven
 * @since: 2024/7/11 13:28
 */
import com.seven.entity.TProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<TProject, Integer> {
}


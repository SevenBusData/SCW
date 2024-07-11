package com.seven.repository;

/**
 * @author: seven
 * @since: 2024/7/11 13:28
 */
import com.seven.entity.TProjectImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectImagesRepository extends JpaRepository<TProjectImages, Integer> {
    List<TProjectImages> findByProjectid(Integer projectId);
}
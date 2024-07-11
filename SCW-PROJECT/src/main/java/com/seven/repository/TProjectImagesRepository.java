package com.seven.repository;

import com.seven.entity.TProjectImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: seven
 * @since: 2024/7/11 16:44
 */
public interface TProjectImagesRepository extends JpaRepository<TProjectImages, Integer> {

    List<TProjectImages> findByProjectid(Integer projectId);
}
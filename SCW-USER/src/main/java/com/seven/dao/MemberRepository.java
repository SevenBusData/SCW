package com.seven.dao;

/**
 * @author: seven
 * @since: 2024/7/11 10:50
 */
import com.seven.entity.TMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<TMember, Integer> {
    public TMember findByLoginacctEquals(String loginacct);
}
package com.seven.dao;

/**
 * @author: seven
 * @since: 2024/7/11 11:00
 */
import com.seven.entity.TMemberAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAddressRepository extends JpaRepository<TMemberAddress, Integer> {
    List<TMemberAddress> findByMemberid(Integer memberId);
}
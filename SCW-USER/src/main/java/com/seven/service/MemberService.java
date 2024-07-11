package com.seven.service;

import com.seven.entity.TMember;
import com.seven.entity.TMemberAddress;

import java.util.List;

/**
 * @author: seven
 * @since: 2024/7/11 10:50
 */
public interface MemberService {
    public void registerUser(TMember member);

    public TMember login(String username, String password);

    public List<TMemberAddress> findAddressByMemberId(Integer memberId);

    //根据用户id，获取用户信息
    public TMember findTmemberById(Integer id);
}

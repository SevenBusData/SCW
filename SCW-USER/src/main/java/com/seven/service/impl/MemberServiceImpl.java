package com.seven.service.impl;

/**
 * @author: seven
 * @since: 2024/7/11 10:51
 */
import com.seven.dao.MemberAddressRepository;
import com.seven.dao.MemberRepository;
import com.seven.entity.TMember;
import com.seven.entity.TMemberAddress;
import com.seven.enums.UserExceptionEnum;
import com.seven.exception.UserException;
import com.seven.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberAddressRepository memberAddressRepository;

    @Override
    public void registerUser(TMember member) {
        //1、检查手机号是否被注册
        TMember m = memberRepository.findByLoginacctEquals(member.getLoginacct());
        if (m != null) {
            throw new UserException(UserExceptionEnum.LOGINACCT_EXIST);
        }
        //2、手机号未被注册，设置相关参数，保存注册信息
        //2.1 设置密码加密，采用Bcrypt算法加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodepassword = passwordEncoder.encode(member.getUserpswd());
        //设置加密后的密码到用户对象
        member.setUserpswd(encodepassword);
        //设置用户名
        member.setUsername(member.getLoginacct());
        //设置实名认证状态 0 - 未实名认证， 1 - 实名认证申请中， 2 - 已实名认证
        member.setAuthstatus("0");
        //用户类型: 0 - 个人， 1 - 企业
        member.setUsertype("0");
        //账户类型: 0 - 企业， 1 - 个体， 2 - 个人， 3 - 政府
        member.setAccttype("2");
        //保存到数据库
        //  memberMapper.insert(member);
        memberRepository.save(member);

        log.info("保存用户信息到数据:" + member.getLoginacct());

    }

    @Override
    public TMember login(String username, String password) {
        TMember tMember = memberRepository.findByLoginacctEquals(username);
        //如果账户不存在则返回null
        if (tMember == null) return null;
        //否则进行密码验证
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches(password, tMember.getUserpswd());
        //验证密码通过返回用户，否则返回null
        return matches ? tMember : null;
    }


    @Override
    public List<TMemberAddress> findAddressByMemberId(Integer memberId) {
        return memberAddressRepository.findByMemberid(memberId);
    }

    @Override
    public TMember findTmemberById(Integer id) {
        return memberRepository.findById(id).get();
    }
}


package org.clxmm.springsecurity02.config.security.dto.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.clxmm.springsecurity02.config.security.dto.SecurityUser;
import org.clxmm.springsecurity02.modules.system.entity.User;
import org.clxmm.springsecurity02.modules.system.entity.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p> 自定义userDetailsService - 认证用户详情 </p>
 *
 * @author clx
 * @date 2020/7/9 16:09
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //读取用户信息
        List<User> users = userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getUsername, username));

        User user;

        if (!CollectionUtils.isEmpty(users)) {
            user = users.get(0);
        } else {
            throw new UsernameNotFoundException("用户名不存在!");
        }

        return new SecurityUser(user);
    }
}

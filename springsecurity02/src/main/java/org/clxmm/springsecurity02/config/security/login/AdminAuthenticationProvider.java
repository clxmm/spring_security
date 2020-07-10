package org.clxmm.springsecurity02.config.security.login;

import org.clxmm.springsecurity02.common.entity.util.PasswordUtils;
import org.clxmm.springsecurity02.config.security.dto.SecurityUser;
import org.clxmm.springsecurity02.config.security.dto.service.impl.UserDetailsServiceImpl;
import org.clxmm.springsecurity02.modules.common.dto.output.ApiResult;
import org.clxmm.springsecurity02.modules.common.entity.util.ResponseUtils;
import org.clxmm.springsecurity02.modules.system.entity.User;
import org.clxmm.springsecurity02.modules.system.entity.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p> 自定义认证处理 </p>
 *
 * @author clx
 * @date 2020/7/9 16:33
 */
@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取前端表单中输入后返回的用户名、密码
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        SecurityUser userInfo = (SecurityUser) userDetailsService.loadUserByUsername(userName);


        String salt = userInfo.getCurrentUserInfo().getSalt();
        boolean isValid = PasswordUtils.isValidPassword(password, userInfo.getPassword(), salt);
        if (!isValid) {
            throw new BadCredentialsException("密码错误！");
        }

        // 前后端分离情况下 处理逻辑...
        // 更新登录令牌 - 之后访问系统其它接口直接通过token认证用户权限...
        String token = PasswordUtils.encodePassword(System.currentTimeMillis() + salt, salt);

        User user = userMapper.selectById(userInfo.getCurrentUserInfo().getId());
        user.setToken(token);
        userMapper.updateById(user);
        userInfo.getCurrentUserInfo().setToken(token);
        return new UsernamePasswordAuthenticationToken(userInfo, password, userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }


}

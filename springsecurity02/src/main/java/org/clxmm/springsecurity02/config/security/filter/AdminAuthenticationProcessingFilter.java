package org.clxmm.springsecurity02.config.security.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.clxmm.springsecurity02.config.Constants;
import org.clxmm.springsecurity02.config.security.CusAuthenticationManager;
import org.clxmm.springsecurity02.config.security.login.AdminAuthenticationFailureHandler;
import org.clxmm.springsecurity02.config.security.login.AdminAuthenticationSuccessHandler;
import org.clxmm.springsecurity02.modules.common.entity.util.MultiReadHttpServletRequest;
import org.clxmm.springsecurity02.modules.system.entity.User;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p> 自定义用户密码校验过滤器 </p>
 *
 * @author clx
 * @date 2020/7/9 16:28
 */
@Component
@Slf4j
public class AdminAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {


    /**
     * @param authenticationManager:             认证管理器
     * @param adminAuthenticationSuccessHandler: 认证成功处理
     * @param adminAuthenticationFailureHandler: 认证失败处理
     */
    public AdminAuthenticationProcessingFilter(CusAuthenticationManager authenticationManager, AdminAuthenticationSuccessHandler adminAuthenticationSuccessHandler, AdminAuthenticationFailureHandler adminAuthenticationFailureHandler) {
        super(new AntPathRequestMatcher("/login", "POST"));
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationSuccessHandler(adminAuthenticationSuccessHandler);
        this.setAuthenticationFailureHandler(adminAuthenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (request.getContentType() == null || !request.getContentType().contains(Constants.REQUEST_HEADERS_CONTENT_TYPE)) {
            throw new AuthenticationServiceException("请求头类型不支持: " + request.getContentType());
        }

        UsernamePasswordAuthenticationToken authRequest;
        try {
            MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(request);
//            将前端传递的数据转换成jsonBean数据格式
            User user = JSONObject.parseObject(wrappedRequest.getBodyJsonStrByForm(wrappedRequest), User.class);
            authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null);
            authRequest.setDetails(authenticationDetailsSource.buildDetails(wrappedRequest));
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}

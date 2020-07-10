package org.clxmm.springsecurity02.config.security.login;

import org.clxmm.springsecurity02.config.security.dto.SecurityUser;
import org.clxmm.springsecurity02.modules.common.dto.output.ApiResult;
import org.clxmm.springsecurity02.modules.common.entity.util.ResponseUtils;
import org.clxmm.springsecurity02.modules.system.entity.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p> 认证成功处理 </p>
 *
 * @author clx
 * @date 2020/7/9 17:04
 */
@Configuration
public class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
        User user = new User();
        SecurityUser securityUser = ((SecurityUser) auth.getPrincipal());
        user.setToken(securityUser.getCurrentUserInfo().getToken());
        ResponseUtils.out(response, ApiResult.ok("登录成功!", user));

    }
}


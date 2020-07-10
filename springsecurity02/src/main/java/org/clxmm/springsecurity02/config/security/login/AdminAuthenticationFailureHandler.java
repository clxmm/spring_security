package org.clxmm.springsecurity02.config.security.login;

import lombok.extern.slf4j.Slf4j;
import org.clxmm.springsecurity02.modules.common.dto.output.ApiResult;
import org.clxmm.springsecurity02.modules.common.entity.util.ResponseUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.events.Event;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p> 认证失败处理器 </p>
 *
 * @author clx
 * @date 2020/7/9 17:27
 */
@Component
@Slf4j
public class AdminAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ApiResult result;
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            result = ApiResult.fail(e.getMessage());
        } else if (e instanceof LockedException) {
            result = ApiResult.fail("账户被锁定");
        } else if (e instanceof CredentialsExpiredException) {
            result = ApiResult.fail("证书过期");
        } else if (e instanceof AccountExpiredException) {
            result = ApiResult.fail("账户过期！");
        } else if (e instanceof DisabledException) {
            result = ApiResult.fail("账户禁用");
        } else {
            log.error("登录失败！", e);
            result = ApiResult.fail("登录失败");
        }
        ResponseUtils.out(response, result);
    }

}

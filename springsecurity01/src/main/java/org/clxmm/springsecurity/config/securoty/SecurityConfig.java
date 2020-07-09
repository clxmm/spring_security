package org.clxmm.springsecurity.config.securoty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;

/**
 * @author clx
 * @date 2020/7/9 10:37
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * inMemoryAuthentication  在内存中配置用户信息
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("clx1").password(passwordEncoder().encode("123456")).roles("admin")
                .and()
                .withUser("clx2").password(passwordEncoder().encode("123456")).roles("user");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // 访问 /index 这个接口，需要具备 admin 角色
                .antMatchers("/index").hasRole("admin")
                // 允许匿名的url 可理解为放行接口 多个使用,分割
                .antMatchers("/","/home").permitAll()
                //其余的都需要认证
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //登录认证界面
                .loginPage("/login")
                //登录成功处理的方式  方式①
                .loginProcessingUrl("/home")
                // 自定义登陆用户名和密码属性名，默认为 username和password
                .usernameParameter("username")
                .passwordParameter("password")
                // 登录成功处理方式 方式②
                .successHandler((request,response,exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write("登录成功...");
                    out.flush();
                })
                //和表单登录的接口直接通过
                .permitAll()
                .and()
                .logout().logoutUrl("/logout")
                //注销成功的回调
                .logoutSuccessHandler((request,response,exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write("登录成功...");
                    out.flush();
                })
                .and()
                .httpBasic()
                .and()
                //关闭csrf
                .csrf().disable();




    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略url - 会直接过滤该url - 将不会经过Spring Security过滤器链
        web.ignoring().antMatchers("/getUserInfo");
        // 忽略的文件夹
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

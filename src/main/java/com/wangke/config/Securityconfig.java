package com.wangke.config;

import com.wangke.handler.myAuthenticationSuccessHander;
import com.wangke.handler.myAuthnticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;


@EnableWebSecurity
public class Securityconfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private myAuthenticationSuccessHander myAuthenticationSuccessHander;

    @Autowired
    private myAuthnticationFailureHandler myAuthenticationFailureHandler;


    @Resource
    private UserDetailsService myuserDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //首页所有人都可以访问，功能页只有对应有权限的人才能访问
        //请求授权的规则~
        http.authorizeRequests()
                .antMatchers(".").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");
        //没有权限会跳转到登陆页面
        http.formLogin()
                .loginPage("/toLogin")//用户未登录时，访问任何 资源都跳转到该路径，即登录页面
                .usernameParameter("user")//自己登录页面定义的用户名的name
                .passwordParameter("pwd")//自己登录页面定义的密码的name
                .loginProcessingUrl("/login")//登录表单form中action的地址，也就是处理请求时的路径
                .defaultSuccessUrl("/")//登录成功时的页面
                .failureForwardUrl("/error")//登录失败跳转页面
                .successHandler(myAuthenticationSuccessHander)//处理json成功时返回的
                .failureHandler(myAuthenticationFailureHandler);//处理json失败是返回的
        http.csrf().disable();//关闭跨站伪造攻击
        http.logout().logoutSuccessUrl("/");//开始注销功能
        http.rememberMe().rememberMeParameter("remeber");//开启记住我功能


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //在内存中取用户
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("zhouzao").password(new BCryptPasswordEncoder().encode("117690")).roles("vip2","vip3")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("117690")).roles("vip1","vip2","vip3")
                .and()
                .withUser("guest").password(new BCryptPasswordEncoder().encode("117690")).roles("vip3");

        //数据库认证的方式
        auth.userDetailsService(myuserDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }
}

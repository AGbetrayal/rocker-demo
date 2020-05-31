package com.example.rockerproductdemo.security.config;

import com.example.rockerproductdemo.security.filter.JwtAuthenticationTokenFilter;
import com.example.rockerproductdemo.security.jwt.JwtAuthenticationFilter;
import com.example.rockerproductdemo.security.response.RestAuthenticationEntryPoint;
import com.example.rockerproductdemo.security.response.RestfulAccessDeniedHandler;
import com.example.rockerproductdemo.security.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author AGbetrayal
 * @date 2020/5/28 21:08
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomUserService customUserService;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    @Qualifier("authenticationSuccessHandler")
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    @Qualifier("authenticationFailHandler")
    private AuthenticationFailHandler failHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * 自定义认证规则(登录校验)
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .formLogin()
                .loginPage("/login.ftl")
                .loginProcessingUrl("/login")
//                .failureForwardUrl("/test.do")
                .successHandler(successHandler)
                .failureHandler(failHandler)
//                .successForwardUrl("/loginSuccess")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/*", "/login.ftl", "/test.do", "/getUser", "login.ftl").permitAll()
                //.antMatchers("/*").hasRole("VIP")
                .anyRequest().fullyAuthenticated()
                /*.authenticated()*/.and().csrf().disable()
                .sessionManagement()// 基于token，所以不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;

        // 禁用缓存
        http.headers().cacheControl();
        // 添加JWT filter  先jwt认证再登录认证
         http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
       /* http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);*/
    }

    @Bean
//    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
    public JwtAuthenticationFilter jwtAuthenticationTokenFilter(){
//        return new JwtAuthenticationTokenFilter();
        return new JwtAuthenticationFilter();
    }

    /*
     * 忽略一些静态资源请求的URL
     * */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers(
                "/**/*.js", "/**/*.css", "/**/*.js", "/**/*.woff", "/**/*.ttf", "/js/**", "/css/**",
                "/**/*.jpg", "/**/*.png", "/**/*.jpeg", "/test/*" , "/img/**"
        );
    }
}

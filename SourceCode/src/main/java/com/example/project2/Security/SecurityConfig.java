//package com.example.project2.Security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/foo/**").permitAll() // Cho phép truy cập vào /foo mà không cần xác thực
//                .anyRequest().authenticated() // Yêu cầu xác thực cho tất cả các trang khác
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .permitAll()
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(loginUrlAuthenticationEntryPoint())
//                .and()
//                .csrf().disable(); // Vô hiệu hóa CSRF cho đơn giản
//    }
//
//    @Bean
//    public AuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
//        LoginUrlAuthenticationEntryPoint entryPoint = new LoginUrlAuthenticationEntryPoint("/login");
//        entryPoint.setUseForward(true); // Sử dụng forward thay vì redirect để giữ lại thông tin request
//        return entryPoint;
//    }
//
//    // Các cấu hình khác (nếu có)...
//
//}

package com.wendy.blogapiwithspringsecurity.configuration.securityConfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityFilterChainConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilterConfiguration filterConfiguration;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/users/**")
                .permitAll()
               .and()
               .authorizeHttpRequests()
               .requestMatchers(POST, "/api/v1/admin/**").hasAuthority("ADMIN")
               .and()
               .authorizeHttpRequests()
               .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority("ADMIN")
               .and()
               .authorizeHttpRequests()
               .requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority("ADMIN")
               .and()
               .authorizeHttpRequests()
               .requestMatchers(POST, "api/v1/comments/**").hasAnyAuthority("ADMIN","REGULAR")
               .and()
               .authorizeHttpRequests()
               .requestMatchers(DELETE, "api/v1/comments/**").hasAnyAuthority("ADMIN","REGULAR")
               .and()
               .authorizeHttpRequests()
               .requestMatchers(POST, "api/v1/likes/**").hasAnyAuthority("ADMIN","REGULAR")
               .and()
               .authorizeHttpRequests()
               .requestMatchers(DELETE, "api/v1/likes/**").hasAnyAuthority("ADMIN","REGULAR")
               .and()
               .authorizeHttpRequests()
               .requestMatchers(POST, "/api/v1/posts/**").hasAnyAuthority("ADMIN", "REGULAR")
               .and()
               .authorizeHttpRequests()
               .requestMatchers(DELETE, "/api/v1/posts/**").hasAnyAuthority("ADMIN", "REGULAR")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(filterConfiguration, UsernamePasswordAuthenticationFilter.class)
       .build();
    }
}




//@Configuration
//@Slf4j
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//    private final AuthenticationProvider authenticationProvider;
//    private final JwtAuthorizationFilter jwtAuthorizationFilter;
//
//    public static final String[] WHITE_LIST_URLS = {
//            "/api/fashion-blog/auth/user/login",
//            "/api/fashion-blog/auth/user/sign-up",
//            "/api/fashion-blog/auth/admin/sign-up",
//            "/v3/api-docs.yaml",
//            "/v3/api-docs/**",
//            "/swagger-ui/**",
//            "/swagger-ui.html"
//    };
//
//    @Bean
//    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(STATELESS)
//                .and().authorizeHttpRequests().requestMatchers(WHITE_LIST_URLS).permitAll()
//                .and().authorizeHttpRequests().requestMatchers(POST, "/api/fashion-blog/posts/**").hasAuthority("ADMIN")
//                .and().authorizeHttpRequests().requestMatchers(PUT, "/api/fashion-blog/posts/update/**").hasAuthority(String.valueOf(Role.ADMIN))
//                .and().authorizeHttpRequests().requestMatchers(DELETE, "/api/fashion-blog/posts/**").hasAuthority(String.valueOf(Role.ADMIN))
//                .and().authorizeHttpRequests().requestMatchers(POST, "/api/fashion-blog/post/comment/**").hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.USER))
//                .and().authorizeHttpRequests().requestMatchers(PUT, "/api/fashion-blog/post/comment/**").hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.USER))
//                .and().authorizeHttpRequests().requestMatchers(DELETE, "/api/fashion-blog/post/comment/**").hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.USER))
//                .and().authorizeHttpRequests().requestMatchers("/api/fashion-blog/comment/**").hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.USER))
//                .and().authorizeHttpRequests().requestMatchers(GET, "/api/fashion-blog/post/comment/**").hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.USER), String.valueOf(Role.ANONYMOUS_USER))
//                .and().authorizeHttpRequests().requestMatchers(GET, "/api/fashion-blog/posts/**").hasAnyAuthority(String.valueOf(Role.ADMIN), String.valueOf(Role.USER), String.valueOf(Role.ANONYMOUS_USER))
//                .and().authorizeHttpRequests().anyRequest().authenticated()
//                .and()
//                .authenticationProvider(authenticationProvider) // injects the authProvider
//                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class); // adds the jwtAuthorizationFilter before the usernamePasswordAuthentication filter for each request
//
//        return http.build();
//    }
//}
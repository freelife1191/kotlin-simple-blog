package com.example.simpleblog.config.security

import com.example.simpleblog.domain.HashMapRepositoryImpl
import com.example.simpleblog.domain.InMemoryRepository
import com.example.simpleblog.domain.RedisRepositoryImpl
import com.example.simpleblog.domain.member.Role
import com.example.simpleblog.util.JsonUtils
import com.example.simpleblog.util.Script.responseData
import com.example.simpleblog.util.value.CommonResDto
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.CustomLog
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/**
 * Created by mskwon on 2024/01/13.
 */
@Configuration
@EnableWebSecurity(debug = false)
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) // @Secured 어노테이션 활성화
class SecurityConfig(
    private val authenticationConfiguration: AuthenticationConfiguration,
    private val redisTemplate: RedisTemplate<String, Any>
) {

    private val log = KotlinLogging.logger { }

    // @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer { web: WebSecurity -> web.ignoring().requestMatchers("/**") }
        // h2-console 사용 및 resources 접근 허용 설정
        // return WebSecurityCustomizer { web: WebSecurity ->
        //     web.ignoring()
        //         .requestMatchers(PathRequest.toH2Console())
        //         .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        // }
    }

    @Bean
    fun filterChainBasic(http: HttpSecurity): SecurityFilterChain {

        http.csrf { it.disable() }
            .headers { it.frameOptions { opt -> opt.disable() } }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .cors { it.configurationSource(corsConfig()) }
            .addFilter(loginFilter())
            .addFilter(authenticationFilter())
            .exceptionHandling {
                it.accessDeniedHandler(CustomaAccessDeniedHandler())
                    .authenticationEntryPoint(CustomAuthenticationEntryPoint())
            }
            .authorizeHttpRequests {
                it.requestMatchers("/v1/posts").hasAnyRole(Role.USER.name, Role.ADMIN.name)
                    .anyRequest().permitAll()
                // it.requestMatchers("/**").authenticated()
            }
            .logout { it.logoutUrl("/logout").logoutSuccessHandler(CustomLogoutSuccessHandler()) }
        return http.build()
    }

    class CustomLogoutSuccessHandler : LogoutSuccessHandler {

        private val log = KotlinLogging.logger { }

        override fun onLogoutSuccess(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authentication: Authentication?
        ) {
            log.info { "logout success" }
            val context = SecurityContextHolder.getContext()
            context.authentication = null
            SecurityContextHolder.clearContext()

            val commonResDto = CommonResDto(HttpStatus.OK, "logout success", null)
            responseData(response, JsonUtils.toMapperJson(commonResDto))
        }

    }

    class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {
        private val log = KotlinLogging.logger {  }
        override fun commence(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authException: AuthenticationException?
        ) {
            log.info { "authentication entry point!!!!" }
            response?.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.reasonPhrase)
            // responseData(response, JsonUtils.toMapperJson(CommonResDto(HttpStatus.UNAUTHORIZED, "Access Denied", response)))
            // response?.sendError(HttpServletResponse.SC_FORBIDDEN)
        }
    }

    class CustomFailureHandler: AuthenticationFailureHandler {
        private val log = KotlinLogging.logger {  }
        override fun onAuthenticationFailure(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            exception: AuthenticationException?
        ) {
            log.warn { "AuthenticationFailure!!!!" }
            response?.sendError(HttpServletResponse.SC_FORBIDDEN, "인증 실패")
        }
    }

    class CustomSuccessHandler: AuthenticationSuccessHandler {
        private val log = KotlinLogging.logger {  }
        override fun onAuthenticationSuccess(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            authentication: Authentication?
        ) {
            log.info { "login Success!!!!" }
        }
    }

    class CustomaAccessDeniedHandler: AccessDeniedHandler {
        private val log = KotlinLogging.logger {  }

        override fun handle(
            request: HttpServletRequest?,
            response: HttpServletResponse?,
            accessDeniedException: AccessDeniedException?
        ) {
            log.info { "access denied!!!!" }
            response?.sendError(HttpServletResponse.SC_FORBIDDEN)
        }
    }

    @Bean
    fun inMemoryRepository(): InMemoryRepository {
        // return HashMapRepositoryImpl()
        return RedisRepositoryImpl(this.redisTemplate)
    }

    @Bean
    fun authenticationFilter(): CustomBasicAuthenticationFilter {
        return CustomBasicAuthenticationFilter(
            authenticationManager = authenticationManager(),
            memoryRepository = inMemoryRepository()
        )
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder();
    }

    @Bean
    fun loginFilter(): UsernamePasswordAuthenticationFilter {
        val authenticationFilter = CustomUserNameAuthenticationFilter(inMemoryRepository())
        authenticationFilter.setAuthenticationManager(authenticationManager())
        authenticationFilter.setFilterProcessesUrl("/login")
        authenticationFilter.setAuthenticationFailureHandler(CustomFailureHandler())
        authenticationFilter.setAuthenticationSuccessHandler(CustomSuccessHandler())
        return authenticationFilter
    }

    @Bean
    fun corsConfig(): CorsConfigurationSource {
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.allowedOrigins = listOf("*")
        config.allowedMethods = listOf("*")
        config.allowedHeaders = listOf("*")
        config.exposedHeaders = listOf("authorization")
        config.maxAge = 3000L
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }

}
package com.savrona.agilepoker.config

import com.savrona.agilepoker.token.TokenRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.NonNull
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@RequiredArgsConstructor
class JWTAuthenticationFilter @Autowired public constructor(
    private val jwtService : JWTService = JWTService(),
    private val userDetailsService: UserDetailsService,
    private val tokenRepository : TokenRepository,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: @NonNull HttpServletRequest,
        response: @NonNull HttpServletResponse,
        filterChain: @NonNull FilterChain
    )  {

        val authHeader : String? = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }
        val jwtToken : String = authHeader.substring(7)
        val username : String = jwtService.extractUserName(jwtToken)

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            val userDetails = userDetailsService.loadUserByUsername(username)
//            val isTokenValid: Unit = tokenRepository.findByToken(jwt)
//                .map { t -> !t.isExpired() && !t.isRevoked() }
//                .orElse(false)
            if (jwtService.isTokenValid(jwtToken, userDetails) /*&& isTokenValid*/) {
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }

        }
        TODO("Not yet implemented")
    }
}


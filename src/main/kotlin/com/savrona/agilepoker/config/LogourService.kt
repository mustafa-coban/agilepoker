package com.savrona.agilepoker.config


import com.savrona.agilepoker.token.Token
import com.savrona.agilepoker.token.TokenRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Service


@Service
@RequiredArgsConstructor
class LogoutService : LogoutHandler {
    private val tokenRepository: TokenRepository? = null
    override fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val authHeader = request.getHeader("Authorization")
        val jwt: String
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return
        }
        jwt = authHeader.substring(7)
        val storedToken: Token? = tokenRepository?.findByToken(jwt)
        if (storedToken != null) {
            storedToken.expired = true
            storedToken.revoked = true
            tokenRepository!!.save(storedToken)
            SecurityContextHolder.clearContext()
        }
    }
}
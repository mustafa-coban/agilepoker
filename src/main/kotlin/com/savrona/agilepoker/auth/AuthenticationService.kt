package com.savrona.agilepoker.auth

import com.savrona.agilepoker.config.JWTService
import com.savrona.agilepoker.token.Token
import com.savrona.agilepoker.token.TokenRepository
import com.savrona.agilepoker.token.TokenType
import com.savrona.agilepoker.user.User
import com.savrona.agilepoker.user.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
@RequiredArgsConstructor
class AuthenticationService {
    private val repository: UserRepository? = null
    private val tokenRepository: TokenRepository? = null
    private val passwordEncoder: PasswordEncoder? = null
    private val jwtService: JWTService? = null
    private val authenticationManager: AuthenticationManager? = null
    fun register(request: RegisterRequest?): AuthenticationResponse {
        val user: User? = request?.let {
            User(
                name = it.name,
                username = request.username,
                eMail = request.email,
                password = request.password,
                role = request.role
            )
        }

        val savedUser = user?.let { repository!!.save<User>(it) }
        val jwtToken: String? = savedUser?.let { jwtService?.generateToken(userDetails = it) }
        jwtToken?.let { saveUserToken(savedUser, it) }
        return AuthenticationResponse(
            token = jwtToken
        )
    }

    fun authenticate(request: AuthenticationRequest?): AuthenticationResponse {
        authenticationManager!!.authenticate(
            UsernamePasswordAuthenticationToken(
                request?.userName ?: "",
                request?.password ?: ""
            )
        )
        val user: User? = request?.userName?.let { repository?.findOneByUserName(it) }
        val jwtToken: String? = user?.let { jwtService?.generateToken(it) }
        user?.let { revokeAllUserTokens(it) }
        user?.let { jwtToken?.let { it1 -> saveUserToken(it, it1) } }
        return AuthenticationResponse(token = jwtToken)
    }

    private fun saveUserToken(user: User, jwtToken: String) {
        val token: Token = Token(
            token=jwtToken,
            tokenType=TokenType.BEARER,
            expired = false,
            revoked = false,
            user = user,
        )
        tokenRepository?.save(token)
    }

    private fun revokeAllUserTokens(user: User) {
        val validUserTokens: List<Token>? = tokenRepository!!.findAllValidTokenByUser(user.id)
        if (validUserTokens?.isEmpty() == true) return
        validUserTokens?.forEach { let { token ->
            token.setExpired(true)
            token.setRevoked(true)
        }
        }
        validUserTokens?.let { tokenRepository!!.saveAll(validUserTokens) }
    }

    private fun setRevoked(b: Boolean) {
        TODO("Not yet implemented")
    }

    private fun setExpired(b: Boolean) {
        TODO("Not yet implemented")
    }
}

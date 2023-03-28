package com.savrona.agilepoker.config

import com.savrona.agilepoker.user.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import java.util.function.Function

@Service
class JWTService (private val secretKey: String = ("58703272357538782F413F4428472B4B6250655368566D597133743676397924")) {

    public fun extractUserName(jwtToken : String) : String {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public fun <T> extractClaim(jwtToken: String, claimsResolver: Function<Claims, T>): T {
        val claims: Claims = extractAllClaims(jwtToken)
        return claimsResolver.apply(claims)
    }

    fun generateToken(userDetails: User): String? {
        return generateToken( emptyMap(), userDetails);
    }

    public fun generateToken(
        extraClaims: Map<String, Object>,
        userDetails : UserDetails,
    ): String? {
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public fun isTokenValid(token : String, userDetails: UserDetails) : Boolean {
        val userName: String = extractUserName(token)
        return (userName.equals(userDetails.username) && !isTokenExpeired(token))

    }

    private fun isTokenExpeired(token: String): Boolean {
        return extractExperiation(token).before(Date(System.currentTimeMillis()))
    }

    private fun extractExperiation(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    private fun extractAllClaims(jwtToken : String) : Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJwt(jwtToken)
            .body
    }
    private fun getSignInKey(): Key {
        val keyBytes  =  Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }


}
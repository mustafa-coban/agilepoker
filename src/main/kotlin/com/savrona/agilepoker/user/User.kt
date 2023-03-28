package com.savrona.agilepoker.user


import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import java.time.LocalDateTime
import java.util.*

@Data
@Document(collection = "User")
@AllArgsConstructor
@NoArgsConstructor
data class User(
    @Id internal val id: ObjectId = ObjectId.get(),

    private val name: String,
    private val role: Role, // Company / ScrumMaster  !!! Developer doesn't need an account
    @Indexed(unique = true)
    private var eMail: String ? = null,
    private val createdDate: LocalDateTime = LocalDateTime.now(),
    private val modifiedDate: LocalDateTime = LocalDateTime.now(),
    private val lastLogin: LocalDateTime = LocalDateTime.now(),
    @Indexed(unique = true)
    private val username: String ? = null,
    private val password: String ? = null,
    private val enabled: Boolean = role == Role.DEVELOPER,
    private val accountNonExpired: Boolean = true,
    private val credentialsNonExpired: Boolean = true,
    private val accountNonLocked: Boolean = true,
    private var authorities: MutableCollection<out GrantedAuthority>? = null,
    @Indexed
    private val serialVersionUID: Long = 600L,
    @Transient
    private val logger: Log = LogFactory.getLog(User::class.java),
) : UserDetails , CredentialsContainer, UserDetailsService {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return listOf((SimpleGrantedAuthority(role.name))).toMutableList()
    }

    override fun getPassword(): String? {
       return password
    }

    override fun getUsername(): String? {
        return username;
    }

    override fun isAccountNonExpired(): Boolean {
        return isAccountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return isAccountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return isCredentialsNonExpired
    }

    override fun isEnabled(): Boolean {
        return isEnabled
    }

    override fun eraseCredentials() {
        TODO("Not yet implemented")
    }

    @Deprecated("")
    fun withDefaultPasswordEncoder(): User.UserBuilder? {
        logger.warn("User.withDefaultPasswordEncoder() is considered unsafe for production and is only intended for sample applications.")
        val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        val var10000 = User.builder()
        Objects.requireNonNull(encoder)
        return var10000.passwordEncoder { rawPassword: String? ->
            encoder.encode(
                rawPassword
            )
        }
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        TODO("Not yet implemented")
    }

}




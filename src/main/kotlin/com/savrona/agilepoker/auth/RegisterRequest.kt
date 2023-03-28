package com.savrona.agilepoker.auth

import com.savrona.agilepoker.user.Role
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class RegisterRequest {
    internal val name: String = ""
    internal val role: Role = Role.DEVELOPER
    internal val username: String? = null
    internal val email: String? = null
    internal val password: String? = null
}
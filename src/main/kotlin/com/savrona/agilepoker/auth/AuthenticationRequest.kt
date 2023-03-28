package com.savrona.agilepoker.auth

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class AuthenticationRequest {
    internal val userName: String? = null
    internal var password: String? = null
}

package com.savrona.agilepoker.auth

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class AuthenticationResponse(token: String?) {

}

package com.savrona.agilepoker.token

import com.savrona.agilepoker.user.User

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("Token")
class Token(
    @Id
    var id: ObjectId = ObjectId.get(),

    @Indexed(unique = true)
    var token: String? = null,


    var tokenType: TokenType = TokenType.BEARER,
    var revoked: Boolean = false,
    var expired: Boolean = false,


    @DocumentReference
    var user: User? = null,
)
{


}

package com.savrona.agilepoker.token

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : MongoRepository<Token, ObjectId> {
    fun findAllValidTokenByUser(id: ObjectId): List<Token>? {
        TODO()
    }

    fun findByToken(jwt: String): Token? {
        TODO()
    }

}

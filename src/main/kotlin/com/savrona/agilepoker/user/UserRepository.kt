package com.savrona.agilepoker.user

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, ObjectId> {
    fun findOneById(id: ObjectId): User?
    override fun deleteAll() {}

    @Query("{username:'?0'}")
    fun findOneByUserName(userName: String): User? {
        TODO("Not yet implemented")
    }
}
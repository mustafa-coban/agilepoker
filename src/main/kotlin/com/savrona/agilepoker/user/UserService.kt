package com.savrona.agilepoker.user

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService @Autowired private constructor(private val userRepository: UserRepository) : UserDetailsService {


    fun getUserById(id: String): User? {
        return userRepository.findOneById(ObjectId(id))
    }

    fun getUserByUserName(userName: String) : User? {
        return userRepository.findOneByUserName(userName)
    }

    fun createUser(userInfo: UserInfo): User? {
        TODO()
    }

    override fun loadUserByUsername(userName: String?): User? {
        return userName?.let { userRepository.findOneByUserName(it) }
    }
}
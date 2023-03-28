package com.savrona.agilepoker.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController @Autowired constructor (private var userService: UserService) {

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") id: String): Any {
        val user : User = userService.getUserById(id) ?: return ResponseEntity.notFound()
        return ResponseEntity.ok(user)
    }
    @GetMapping("/{userName}")
    fun getUserByUserName(@PathVariable("userName") userName: String): Any {
        val user : User = userService.getUserByUserName(userName) ?: return ResponseEntity.notFound()
        return ResponseEntity.ok(user)
    }

    @PostMapping
    fun createUser(@RequestBody userInfo: UserInfo): Any {
        val user: User = userService.createUser(userInfo) ?: return ResponseEntity.badRequest()
        return ResponseEntity.ok(user)
    }
}
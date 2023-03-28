package com.savrona.agilepoker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@SpringBootApplication
@EnableMongoRepositories
class AgilepokerApplication

fun main(args: Array<String>) {
	runApplication<AgilepokerApplication>(*args)
}

package com.github.daggerok.coroutines.usermessage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UserMessageApplication

fun main(args: Array<String>) {
    runApplication<UserMessageApplication>(*args)
}

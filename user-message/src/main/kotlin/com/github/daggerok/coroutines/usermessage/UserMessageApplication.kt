package com.github.daggerok.coroutines.usermessage

import com.sun.net.httpserver.HttpExchange
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.netty.http.server.HttpServerRequest

data class User(val name: String,
                @Id var id: Long? = null)

data class Message(val body: String,
                   @Id var id: Long? = null)

interface UserRepository : ReactiveCrudRepository<User, Long>
interface MessageRepository : ReactiveCrudRepository<Message, Long>

@RestController
class Resources(private val userRepository: UserRepository,
                private val messageRepository: MessageRepository) {

    @GetMapping
    fun api(exchange: ServerWebExchange) = Mono
            .just(exchange.request.uri)
            .map { it.toURL() }
            .map { "${it.protocol}://${it.authority}" }
            .map {
                mapOf(
                        "users" to "$it/users",
                        "messages" to "$it/messages"
                )
            }

    @GetMapping("/users")
    fun findAllUsers() = userRepository.findAll()

    @GetMapping("/messages")
    fun findAllMessages() = messageRepository.findAll()
}

@SpringBootApplication
@EnableTransactionManagement
class UserMessageApplication

fun main(args: Array<String>) {
    runApplication<UserMessageApplication>(*args)
}

package com.github.daggerok.coroutines.city

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

data class City(val map: String,
                val country: String,
                val state: String,
                val name: String,
                @Id var id: Long? = null)

interface CityRepository : ReactiveCrudRepository<City, Long>

@RestController
class CityResource(private val cityRepository: CityRepository) {

    @GetMapping
    fun findAll() = cityRepository.findAll()
}

@SpringBootApplication
@EnableTransactionManagement
class CityApplication

fun main(args: Array<String>) {
    runApplication<CityApplication>(*args)
}

package com.example.bite_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BiteApplication

fun main(args: Array<String>) {
	runApplication<BiteApplication>(*args)
}

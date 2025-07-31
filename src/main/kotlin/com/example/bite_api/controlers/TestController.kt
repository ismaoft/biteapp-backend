package com.example.bite_api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/public-test")
    fun publicEndpoint(): ResponseEntity<String> {
        return ResponseEntity.ok("Endpoint público funcionando")
    }

    @GetMapping("/free-access")
    fun freeAccess(): ResponseEntity<String> {
        return ResponseEntity.ok("Access granted to free endpoint!")
    }
}

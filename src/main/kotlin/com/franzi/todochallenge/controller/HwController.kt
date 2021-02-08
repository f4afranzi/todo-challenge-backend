package com.franzi.todochallenge.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HwController {

    @GetMapping("/")
    fun helloWorld(): String = "Hello World"
}
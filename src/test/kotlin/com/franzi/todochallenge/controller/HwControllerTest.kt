package com.franzi.todochallenge.controller

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class HwControllerTest {

    @Test
    fun helloWorld() {
        val hwController = HwController()
        var helloWorld = hwController.helloWorld()
        assertEquals("Hello World", helloWorld)
    }
}
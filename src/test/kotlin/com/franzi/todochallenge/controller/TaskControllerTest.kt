package com.franzi.todochallenge.controller

import io.mockk.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.Instant

internal class TaskControllerTest {
    val taskRepository = mockk<TaskRepository>()
    val service = mockk<WhateverService>()
    val taskController = TaskController(taskRepository, service) //.also { print("Hello") }

    @Test
    fun `successfull task creation`() {
        val text = "wash"
        val task = Task(5, text, Instant.now())
        every { taskRepository.insert(text, any()) } returns task
        every { service.printText(any())} just Runs

        var newlyCreatedTask = taskController.createTask(TaskCreationRequestBody(text))

        val expectedText = "wash-5"
        Assertions.assertThat(newlyCreatedTask.text).isEqualTo(expectedText)
        verify(exactly = 1) { taskRepository.insert(text, any()) }
        verify(exactly = 1) { service.printText(expectedText)}
    }

    //@Test
    fun `read task successfully`() {
        taskController.createTask( TaskCreationRequestBody("test1"))
        taskController.createTask( TaskCreationRequestBody("test2"))
        taskController.createTask( TaskCreationRequestBody("test3"))

        val tasks = taskController.readTasks()

        assertEquals(3, tasks.size)
        Assertions.assertThat(tasks.map { it.text }).containsExactlyInAnyOrder("test1", "test2", "test3")
    }

    // TODO: add missing test for read single task by index
}


package com.franzi.todochallenge.controller

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.Instant

internal class TaskControllerTest {
    val taskController = TaskController() //.also { print("Hello") }

    @Test
    fun `successfull task creation`() {
        val taskInput = TaskCreationRequestBody("testTask")

        var newlyCreatedTask = taskController.createTask(taskInput)

        assertEquals(taskInput.text, newlyCreatedTask.text)
        assertTrue(Instant.now() > newlyCreatedTask.creationDate)
        assertTrue(newlyCreatedTask.id >= 0)
    }

    @Test
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


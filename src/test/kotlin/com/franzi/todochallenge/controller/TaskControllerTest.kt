package com.franzi.todochallenge.controller

import io.mockk.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.Instant

internal class TaskControllerTest {
    val taskRepository = mockk<TaskRepository>()
    val taskController = TaskController(taskRepository) //.also { print("Hello") }

    @Test
    fun `successfull task creation`() {
        // preparation
        val text = "wash"
        val task = Task(5, text, Instant.now())
        every { taskRepository.insert(text, any()) } returns task

        // action itself
        var newlyCreatedTask = taskController.createTask(TaskCreationRequestBody(text))

        // verification
        val expectedText = "wash-5"
        Assertions.assertThat(newlyCreatedTask.text).isEqualTo(expectedText)
        verify(exactly = 1) { taskRepository.insert(text, any()) } // verify that the mock method was invoked one time
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


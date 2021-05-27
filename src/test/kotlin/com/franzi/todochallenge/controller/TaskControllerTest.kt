package com.franzi.todochallenge.controller

import com.franzi.todochallenge.repository.TaskRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.*

class TaskControllerTest {
    val taskRepository = mockk<TaskRepository>()
    val taskController = TaskController(taskRepository) //.also { print("Hello") }

    @Test
    fun `successfull task creation`() {
        // preparation
        val text = "wash"
        val task = Task(UUID.randomUUID(), text, Instant.now())
        every { taskRepository.insert(text, any()) } returns task

        // action itself
        var newlyCreatedTask = taskController.createTask(TaskBody(text))

        // verification
        val expectedText = "wash"
        assertThat(newlyCreatedTask.text).isEqualTo(expectedText)
        verify(exactly = 1) { taskRepository.insert(text, any()) } // verify that the mock method was invoked one time
    }

    @Test
    fun `read all tasks successfully`() {
        //prepare
        val task1 = Task(UUID.randomUUID(), "test1", Instant.now())
        val task2 = Task(UUID.randomUUID(), "test2", Instant.now())
        val task3 = Task(UUID.randomUUID(), "test3", Instant.now())
        every { taskRepository.findAll() } returns listOf(task1, task2, task3)

        //action
        val tasks = taskController.readTasks()

        assertThat(tasks).hasSize(3)
        assertThat(tasks.map { it.text }).containsExactlyInAnyOrder("test1", "test2", "test3")
        verify(exactly = 1) { taskRepository.findAll() }
    }

    @Test
    fun `read task by id successfully`() {
        //prepare
        val task2 = Task(UUID.randomUUID(), "test2", Instant.now())
        every { taskRepository.findById(task2.id) } returns task2

        //action
        val task = taskController.findTaskById(task2.id)

        assertThat(task.text).isEqualTo(task2.text)
        verify(exactly = 1) { taskRepository.findById(task2.id) }
    }

    @Test
    fun `update task with id successfully`() {
        //prepare
        val task2 = Task(UUID.randomUUID(), "test2", Instant.now())
        every { taskRepository.update(task2.id, "updatedText") } returns Unit

        //action
        taskController.updateTask(task2.id, TaskBody("updatedText"))

        verify(exactly = 1) { taskRepository.update(task2.id, "updatedText") }
    }
}

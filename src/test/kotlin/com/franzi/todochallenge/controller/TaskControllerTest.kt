package com.franzi.todochallenge.controller

import com.franzi.todochallenge.repository.TaskRepository
import io.mockk.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
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
        Assertions.assertThat(newlyCreatedTask.text).isEqualTo(expectedText)
        verify(exactly = 1) { taskRepository.insert(text, any()) } // verify that the mock method was invoked one time
    }

    @Test
    fun `read all tasks successfully`() {
        //prepare
        val task1 = Task(UUID.randomUUID(), "test1", Instant.now())
        val task2 = Task(UUID.randomUUID(), "test2", Instant.now())
        val task3 = Task(UUID.randomUUID(), "test3", Instant.now())
        every { taskRepository.insert(task1.text, any()) } returns task1
        every { taskRepository.insert(task2.text, any()) } returns task2
        every { taskRepository.insert(task3.text, any()) } returns task3
        every { taskRepository.findAll() } returns listOf(task1, task2, task3)

        //action
        taskController.createTask( TaskBody("test1"))
        taskController.createTask( TaskBody("test2"))
        taskController.createTask( TaskBody("test3"))

        val tasks = taskController.readTasks()

        assertEquals(3, tasks.size)
        Assertions.assertThat(tasks.map { it.text }).containsExactlyInAnyOrder("test1", "test2", "test3")
    }

    @Test
    fun `read task by id successfully`() {
        //prepare
        val task1 = Task(UUID.randomUUID(), "test1", Instant.now())
        val task2 = Task(UUID.randomUUID(), "test2", Instant.now())
        val task3 = Task(UUID.randomUUID(), "test3", Instant.now())
        every { taskRepository.insert(task1.text, any()) } returns task1
        every { taskRepository.insert(task2.text, any()) } returns task2
        every { taskRepository.insert(task3.text, any()) } returns task3
        every { taskRepository.findById(task2.id) } returns task2
        //action
        taskController.createTask( TaskBody("test1"))
        taskController.createTask( TaskBody("test2"))
        taskController.createTask( TaskBody("test3"))

        val task = taskController.findTaskById(task2.id)

        Assertions.assertThat(task.text).isEqualTo(task2.text)
    }

    @Test
    fun `update task with id successfully`() {
        //prepare
        val task1 = Task(UUID.randomUUID(), "test1", Instant.now())
        val task2 = Task(UUID.randomUUID(), "test2", Instant.now())
        val task3 = Task(UUID.randomUUID(), "test3", Instant.now())
        every { taskRepository.insert(task1.text, any()) } returns task1
        every { taskRepository.insert(task2.text, any()) } returns task2
        every { taskRepository.insert(task3.text, any()) } returns task3
        every { taskRepository.findById(task2.id) } returns task2
        //action
        taskController.createTask( TaskBody("test1"))
        taskController.createTask( TaskBody("test2"))
        taskController.createTask( TaskBody("test3"))

        val task = taskController.findTaskById(task2.id)

        Assertions.assertThat(task.text).isEqualTo(task2.text)
    }
}

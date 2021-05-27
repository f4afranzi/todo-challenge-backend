package com.franzi.todochallenge.controller

import com.franzi.todochallenge.repository.TaskRepository
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import java.time.Instant

internal class TaskRepositoryTest {

    private val taskRepository = TaskRepository()

    @Test
    fun `Should find all created Tasks properly`() {
        taskRepository.insert("task-01", Instant.now())
        taskRepository.insert("task-02", Instant.now())

        val tasks = taskRepository.findAll()
        assertThat(tasks).hasSize(2)
        assertThat(tasks.map { it.text }).contains("task-01", "task-02")
    }

    @Test
    fun `Should update specific task correctly`() {
        val task = taskRepository.insert("task-01", Instant.now())

        taskRepository.update(task.id, "task-01.1")

        val tasks = taskRepository.findAll()
        assertThat(tasks).hasSize(1)
        assertThat(tasks.map { it.text }).contains("task-01.1")
    }

    @Test
    fun `Should delete specific task`() {
        val task = taskRepository.insert("task-01", Instant.now())

        taskRepository.delete(task.id)

        val tasks = taskRepository.findAll()
        assertThat(tasks).isEmpty()
    }

}

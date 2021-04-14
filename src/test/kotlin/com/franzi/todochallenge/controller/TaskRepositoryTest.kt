package com.franzi.todochallenge.controller

import com.franzi.todochallenge.repository.TaskRepository
import org.junit.jupiter.api.Test

import org.assertj.core.api.Assertions
import java.time.Instant

internal class TaskRepositoryTest {

    private val taskRepository = TaskRepository()

    @Test
    fun insertAndFind() {
        taskRepository.insert("task-01", Instant.now())
        taskRepository.insert("task-02", Instant.now())

        val tasks = taskRepository.findAll()
        Assertions.assertThat(tasks).hasSize(2)
        Assertions.assertThat(tasks.map { it.text }).contains("task-01", "task-02")
    }

    @Test
    fun update() {
        val task = taskRepository.insert("task-01", Instant.now())

        taskRepository.update(task.id, "task-01.1")

        val tasks = taskRepository.findAll()
        Assertions.assertThat(tasks).hasSize(1)
        Assertions.assertThat(tasks.map { it.text }).contains("task-01.1")
    }

    @Test
    fun delete() {
        val task = taskRepository.insert("task-01", Instant.now())

        taskRepository.delete(task.id)

        val tasks = taskRepository.findAll()
        Assertions.assertThat(tasks).isEmpty()
    }

}

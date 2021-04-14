package com.franzi.todochallenge.controller

import com.franzi.todochallenge.repository.TaskRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("/tasks")
class TaskController(
        private val taskRepository: TaskRepository,
) {

    @PostMapping
    fun createTask(@RequestBody taskBody: TaskBody): Task {
        return taskRepository.insert(taskBody.text, Instant.now())
    }

    @GetMapping("/{id}")
    fun findTaskById(@PathVariable id: UUID): Task {
        return taskRepository.findById(id)
    }

    @GetMapping(produces = ["application/json"])
    fun readTasks(): List<Task> {
        return taskRepository.findAll()
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: UUID) {
        taskRepository.delete(id)
    }

    @PatchMapping("/{id}")
    fun updateTask(@PathVariable id: UUID, @RequestBody taskBody: TaskBody) {
        taskRepository.update(id, taskBody.text)
    }
}

data class Task(val id: UUID, val text: String, val creationDate: Instant = Instant.now())
data class TaskBody (val text: String)

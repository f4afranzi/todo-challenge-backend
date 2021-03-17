package com.franzi.todochallenge.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/tasks")
class TaskController(
        private val taskRepository: TaskRepository,
) {

    @PostMapping
    fun createTask(@RequestBody taskBody: TaskBody): Task {
        return taskRepository.insert(taskBody.text, Instant.now())
    }

    @GetMapping(produces = ["application/json"])
    fun readTasks(): List<Task> {
        return taskRepository.findAll()
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Int) {
        taskRepository.delete(id)
    }

    @PatchMapping("/{id}")
    fun updateTask(@PathVariable id: Int, @RequestBody taskBody: TaskBody) {
        taskRepository.update(id, taskBody.text)
    }
}

data class Task(val id: Int, val text: String, val creationDate: Instant = Instant.now())
data class TaskBody (val text: String)

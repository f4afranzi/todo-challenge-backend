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
    fun createTask(@RequestBody taskRequestBody: TaskCreationRequestBody): Task {
        return taskRepository.insert(taskRequestBody.text, Instant.now())
    }

    @GetMapping
    fun readTasks(): List<Task> {
        return taskRepository.findAll()
    }

    /*@GetMapping("/{index}")
    fun getTaskByIndex(@PathVariable index: Int): Task {
        return tasks.get(index)
    }*/
}

data class Task(val id: Int, val text: String, val creationDate: Instant = Instant.now())
data class TaskCreationRequestBody (val text: String)




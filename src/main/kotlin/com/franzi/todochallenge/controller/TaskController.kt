package com.franzi.todochallenge.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/tasks")
class TaskController {
    val tasks = mutableListOf<Task>()

    @PostMapping
    fun createTask(@RequestBody taskRequestBody: TaskCreationRequestBody): Task {
        val task = Task(id = tasks.size, text = taskRequestBody.text)
        tasks.add(task)
        return task
    }

    @GetMapping
    fun readTasks(): List<Task> {
        return tasks
    }

    @GetMapping("/{index}")
    fun getTaskByIndex(@PathVariable index: Int): Task {
        return tasks.get(index)
    }
}

data class Task(val id: Int, val text: String, val creationDate: Instant = Instant.now())
data class TaskCreationRequestBody (val text: String)




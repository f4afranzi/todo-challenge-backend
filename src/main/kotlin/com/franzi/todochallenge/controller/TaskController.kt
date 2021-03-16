package com.franzi.todochallenge.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/tasks")
class TaskController(
        private val taskRepository: TaskRepository,
        private val service: WhateverService,
) {

    @PostMapping
    fun createTask(@RequestBody taskRequestBody: TaskCreationRequestBody): Task {
        val task = taskRepository.insert(taskRequestBody.text, Instant.now())
        val task2 = task.copy(text = "${task.text}-${task.id}")

        service.printText(task2.text)

        return task2
//        return taskRepository.insert(taskRequestBody.text, Instant.now())
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




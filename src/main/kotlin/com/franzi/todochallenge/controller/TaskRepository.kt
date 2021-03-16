package com.franzi.todochallenge.controller

import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class TaskRepository {
    private val tasks = mutableListOf<Task>()

    fun findAll(): List<Task> {
        return tasks
    }

    fun insert(text: String, creationDate: Instant): Task  {
        val task = Task(id = tasks.size, text = text, creationDate = creationDate)
        tasks.add(task)
        return task
    }
/*
    fun update(task) {

    }

    fun delete(task) {

    }*/
}

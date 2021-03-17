package com.franzi.todochallenge.controller

import org.springframework.stereotype.Repository
import java.lang.RuntimeException
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

    fun update(id: Int, newText: String) {
        // 1: find the target task INDEX
        val index = tasks.indexOfFirst { it.id == id }

        // 2: create task with new text
        val oldTask = tasks[index]
        val newTask = oldTask.copy(text = newText)

        // 3: delete old task
        tasks.removeAt(index)

        // 4: add new task to the list
        tasks.add(newTask)
    }

    fun delete(id: Int) {
        val removed = tasks.removeIf { it.id == id }
        if (!removed) {
            throw RuntimeException("Item with id: $id not found") // TODO: handle it and give back a proper http response to client
        }
    }
}

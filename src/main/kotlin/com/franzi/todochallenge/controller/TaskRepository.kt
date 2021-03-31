package com.franzi.todochallenge.controller

import org.springframework.stereotype.Repository
import java.lang.RuntimeException
import java.time.Instant

@Repository
class TaskRepository {
    private val tasks = mutableListOf<Task>()
    private val tasksMap = mutableMapOf<Int, Task>()

    fun findAll(): List<Task> {
        return tasksMap.toList().map { it.second }
    }

    fun insert(text: String, creationDate: Instant): Task  {
        val task = Task(id = tasks.size, text = text, creationDate = creationDate)
        tasks.add(task)
        tasksMap[task.id] = task
        return task
    }

    fun update(id: Int, newText: String) {
        if (!tasksMap.containsKey(id)) {
            throw RuntimeException("Item with id: $id not found") // TODO: handle it and give back a proper http response to client
        }

        val oldTask = tasksMap[id]!!
        val newTask = oldTask.copy(text = newText)
        // tasksMap[id] = tasksMap[id]!!.copy(text = newText)

        tasksMap[id] = newTask
    }

    fun delete(id: Int) {
        if (!tasksMap.containsKey(id)) {
            throw RuntimeException("Item with id: $id not found") // TODO: handle it and give back a proper http response to client
        }
        tasksMap.remove(id)
    }
}

package com.franzi.todochallenge.repository


import com.franzi.todochallenge.controller.Task
import org.springframework.stereotype.Repository
import java.lang.RuntimeException
import java.time.Instant
import java.util.*

@Repository
class TaskRepository {
    private val tasksMap = mutableMapOf<UUID, Task>()

    fun findAll(): List<Task> {
        return tasksMap.toList().map { it.second }
    }

    fun findById(id: UUID): Task {
        val task = tasksMap[id] //return tasksMap.get(id)
        if (task == null) {
            throw RuntimeException("Task with id: $id not found") // TODO: handle it and give back a proper http response to client
        }

        return task
    }

    fun insert(text: String, creationDate: Instant): Task  {
        val task = Task(id = UUID.randomUUID(), text = text, creationDate = creationDate)
        tasksMap[task.id] = task
        return task
    }

    fun update(id: UUID, newText: String) {
        if (!tasksMap.containsKey(id)) {
            throw RuntimeException("Item with id: $id not found") // TODO: handle it and give back a proper http response to client
        }

        val oldTask = tasksMap[id]!!
        val newTask = oldTask.copy(text = newText)
        // tasksMap[id] = tasksMap[id]!!.copy(text = newText)

        tasksMap[id] = newTask
    }

    fun delete(id: UUID) {
        if (!tasksMap.containsKey(id)) {
            throw RuntimeException("Item with id: $id not found") // TODO: handle it and give back a proper http response to client
        }
        tasksMap.remove(id)
    }
}

package com.franzi.todochallenge.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*


@SpringBootTest
@AutoConfigureMockMvc
internal class TaskControllerIT {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `when posting a task request, task should have been created successfully`() {
        val task1 = TaskBody("testText")
        postTaskToCreate(task1)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.text").value(task1.text))
    }

    @Test
    fun `when sending get request, task is the same as creation one`() {
        val task2 = TaskBody("testText2")
        val taskCreationResponseBody = postTaskToCreate(task2).andReturn().response.contentAsString
        val id = JsonPath.parse(taskCreationResponseBody).read<String>("$.id")
        getSpecificTask(id)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.text").value(task2.text))
                .andExpect(jsonPath("$.id").value(id))
    }

    private fun postTaskToCreate(task: TaskBody) = this.mockMvc.perform(post("/tasks").contentType(MediaType.APPLICATION_JSON).content(toJson(task)))
    private fun getTasks() = this.mockMvc.perform(get("/tasks"))
    private fun getSpecificTask(id: String) = this.mockMvc.perform(get("/tasks/$id"))
}

val objectMapper = ObjectMapper()
fun toJson(instance: Any): String = objectMapper.writeValueAsString(instance)
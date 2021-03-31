package com.franzi.todochallenge.controller

import com.fasterxml.jackson.databind.ObjectMapper
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


@SpringBootTest
@AutoConfigureMockMvc
internal class TaskControllerIT {
    val task = TaskBody("testText")

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `when posting a task request, task should have been created successfully`() {
        postTaskToCreate()
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.text").value("testText"))
                .andExpect(jsonPath("$.id").value(0))
    }

    //@Test
    fun `when sending get request, task is the same as creation response`() {
        postTaskToCreate()
        getTasks()
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))


    }

    private fun postTaskToCreate() = this.mockMvc.perform(post("/tasks").contentType(MediaType.APPLICATION_JSON).content(toJson(task)))
    private fun getTasks() = this.mockMvc.perform(get("/tasks"))
}

val objectMapper = ObjectMapper()
fun toJson(instance: Any): String = objectMapper.writeValueAsString(instance)

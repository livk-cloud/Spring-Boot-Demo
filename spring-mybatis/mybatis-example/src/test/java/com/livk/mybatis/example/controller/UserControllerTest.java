package com.livk.mybatis.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.livk.mybatis.example.entity.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * <p>
 * UserControllerTest
 * </p>
 *
 * @author livk
 * @date 2023/1/5
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    Integer id = 10;

    ObjectMapper mapper = JsonMapper.builder().build();


    @Order(3)
    @Test
    void testGetById() throws Exception {
        mockMvc.perform(get("/user/{id}", id))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("id", id).exists());
    }

    @Order(2)
    @Test
    void testUpdateById() throws Exception {
        User user = new User();
        user.setUsername("livk https");
        mockMvc.perform(put("/user/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("true"));
    }

    @Order(1)
    @Test
    void testSave() throws Exception {
        User user = new User();
        user.setId(id);
        user.setUsername("livk");
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("true"));
    }

    @Order(5)
    @Test
    void testDeleteById() throws Exception {
        mockMvc.perform(delete("/user/{id}", id))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("true"));
    }

    @Order(4)
    @Test
    void testList() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("list.[*].id", id).exists())
                .andExpect(jsonPath("pageNum", 1).exists())
                .andExpect(jsonPath("pageSize", 10).exists());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme
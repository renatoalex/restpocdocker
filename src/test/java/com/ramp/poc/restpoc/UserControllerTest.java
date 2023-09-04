package com.ramp.poc.restpoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramp.poc.restpoc.model.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach
        public void clearDatabase(@Autowired JdbcTemplate jdbcTemplate) {
                JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        }

        @Test
        public void when_gettingAllUsers_With_NoUsers_Expect_NoContent() throws Exception {
                mockMvc.perform(get("/api/users/")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent())
                                .andDo(print());
        }

        @Test
        public void when_gettingAllUsers_With_AtLeastOneUser_Expect_Ok() throws Exception {
                User testUser = new User();
                testUser.setUsername("test");

                mockMvc.perform(post("/api/users/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testUser)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

                mockMvc.perform(get("/api/users/")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andDo(print());
        }

        @Test
        public void when_gettingOneUserById_With_NoUserWithThatId_Expect_NotFound() throws Exception {
                mockMvc.perform(get("/api/users/{id}", -1)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound())
                                .andDo(print());
        }

        @Test
        public void when_gettingOneUserById_With_ExistingUserWithThatId_Expect_Ok() throws Exception {
                User testUser = new User();
                testUser.setUsername("test");

                mockMvc.perform(post("/api/users/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testUser)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

                mockMvc.perform(get("/api/users/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andDo(print());
        }

        @Test
        public void when_gettingOneUserByUsername_With_NoUserWithThatUsername_Expect_NotFound() throws Exception {
                mockMvc.perform(get("/api/users/username/{username}", "notfounduser")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound())
                                .andDo(print());
        }

        @Test
        public void when_gettingOneUserByUsername_With_ExistingUserWithThatExactlyUsername_Expect_Ok()
                        throws Exception {
                User testUser = new User();
                testUser.setUsername("test");

                mockMvc.perform(post("/api/users/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testUser)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

                mockMvc.perform(get("/api/users/username/{username}", "test")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andDo(print());
        }

        @Test
        public void when_creatingUser_With_NewUser_Expect_Created() throws Exception {
                User testUser = new User();
                testUser.setUsername("test");

                mockMvc.perform(post("/api/users/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testUser)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andDo(print());
        }

        @Test
        public void when_creatingUser_With_ExistingUserWithSameUsername_Expect_ExpectationFailed() throws Exception {
                User testUser = new User();
                testUser.setUsername("test");

                mockMvc.perform(post("/api/users/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testUser)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

                mockMvc.perform(post("/api/users/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testUser)))
                                .andExpect(status().isExpectationFailed())
                                .andDo(print());
        }

        @Test
        public void when_updatingUser_With_NonExistingUser_Expect_NotFound() throws Exception {
                User testUser = new User();
                testUser.setUsername("testchanged");

                mockMvc.perform(put("/api/users/{id}", -1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testUser)))
                                .andExpect(status().isNotFound())
                                .andDo(print());
        }

        @Test
        public void when_deletingUser_With_NonExistingUser_Expect_NoContent() throws Exception {
                mockMvc.perform(delete("/api/users/{id}", -1)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent())
                                .andDo(print());
        }

        @Test
        public void when_deletingUser_With_ExistingUserWithThatId_Expect_NoContent() throws Exception {
                User testUser = new User();
                testUser.setUsername("test");

                mockMvc.perform(post("/api/users/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testUser)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

                mockMvc.perform(delete("/api/users/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNoContent())
                                .andDo(print());
        }

}

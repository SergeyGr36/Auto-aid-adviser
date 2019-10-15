package com.hillel.evo.adviser.registration.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.evo.adviser.registration.dto.RegistrationDto;
import com.hillel.evo.adviser.registration.enums.RoleUser;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@Sql(value = {"/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void activation() {
    }

    @Test
    public void registrationReturnCode201() throws Exception {
        // given
        RegistrationDto dto = new RegistrationDto("tt@mail.com", "12345", RoleUser.ROLE_USER);
        //then
        mockMvc.perform(post("/user/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objToJson(dto)))
                .andExpect(status().is(201))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(Matchers.containsString("code")));
    }

    @Test
    public void registrationReturnCode208() throws Exception {
        // given
        RegistrationDto dto = new RegistrationDto("vg@mail.com", "12345", RoleUser.ROLE_USER);
        //then
        mockMvc.perform(post("/user/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objToJson(dto)))
                .andExpect(status().is(208));
    }

    private byte[] objToJson(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return  mapper.writeValueAsBytes(o);
    }
}

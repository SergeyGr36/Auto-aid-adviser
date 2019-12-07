package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.AdviserStarter;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(classes = AdviserStarter.class)
@AutoConfigureMockMvc
@Sql(value = {"/user-profile.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-user-profile.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserCarControllerTest {

}

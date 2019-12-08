package com.hillel.evo.adviser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.dto.HistoryLocationDto;
import com.hillel.evo.adviser.dto.HistoryPointDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.entity.HistoryPoint;
import com.hillel.evo.adviser.mapper.SearchHistoryMapper;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.SearchHistoryRepo;
import com.hillel.evo.adviser.service.EncoderService;
import com.hillel.evo.adviser.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdviserStarter.class)
@AutoConfigureMockMvc
@Sql(value = {"/clean-user.sql", "/create-user2.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(statements = {"TRUNCATE TABLE history_point"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clean-image.sql", "/clean-business.sql", "/clean-user.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SearchHistoryControllerTest {
    private static final String PATH_HISTORY = "/api/user/history";
    private static final String BUSINESS_EMAIL = "svg@mail.com";


    private AdviserUserDetails user;
    private String jwt;
    private HistoryPointDto historyPointDto;
    private HistoryPoint historyPoint;

    @Autowired
    private AdviserUserDetailRepository userRepository;

    @Autowired
    EncoderService encoderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SearchHistoryRepo historyRepo;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    SearchHistoryMapper searchHistoryMapper;

    @Autowired
    JwtService jwtService;

    @BeforeEach
    public void init() {
        encodeTestUserPassword();
        user = userRepository.findByEmail(BUSINESS_EMAIL).get();
        jwt = jwtService.generateAccessToken(user.getId());

        historyPoint = historyRepo.save(searchHistoryMapper.toEntity(new HistoryPointDto(user.getId(), LocalDateTime.now(),
                new HistoryLocationDto(100.1, 101.5), 2L)));
        historyPointDto = searchHistoryMapper.toDto(historyPoint);
    }

    private void encodeTestUserPassword() {
        AdviserUserDetails user = userRepository.findByEmail(BUSINESS_EMAIL).get();
        String password = user.getPassword();
        user.setPassword(encoderService.encode(password));
        userRepository.save(user);
    }

    @Test
    public void findSearchHistoryByUserId() throws Exception {
        mockMvc.perform(get(PATH_HISTORY)
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].userId").value(historyPointDto.getUserId()));
    }

    @Test
    public void saveHistoryPoint() throws Exception {
        mockMvc.perform(post(PATH_HISTORY)
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(historyPointDto)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(historyPointDto.getId()))
                .andExpect(jsonPath("$.userId").value(historyPointDto.getUserId()))
                .andExpect(jsonPath("$.location").value(historyPointDto.getLocation()))
                .andExpect(jsonPath("$.serviceId").value(historyPointDto.getServiceId()));

    }

    @Test
    public void whenClientIdNotEqualsSendIdReturnBadRequest() throws Exception {
        HistoryPointDto historyPointDtoWithAnotherId = new HistoryPointDto(10L, LocalDateTime.now(),
                new HistoryLocationDto(100.1, 101.5), 2L);

        mockMvc.perform(post(PATH_HISTORY)
                .header("Authorization", JwtService.TOKEN_PREFIX + jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(historyPointDtoWithAnotherId)))
                .andExpect(status().isForbidden());
    }
}
package com.hillel.evo.adviser.controller;

import com.hillel.evo.adviser.AdviserStarter;
import com.hillel.evo.adviser.dto.BusinessShortDto;
import com.hillel.evo.adviser.dto.HistoryPointDto;
import com.hillel.evo.adviser.entity.AdviserUserDetails;
import com.hillel.evo.adviser.entity.Business;
import com.hillel.evo.adviser.mapper.HistoryBusinessMapper;
import com.hillel.evo.adviser.repository.AdviserUserDetailRepository;
import com.hillel.evo.adviser.repository.BusinessRepository;
import com.hillel.evo.adviser.service.EncoderService;
import com.hillel.evo.adviser.service.JwtService;
import com.hillel.evo.adviser.service.SearchHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AdviserStarter.class)
@AutoConfigureMockMvc
@Sql(value = {"/clean-all.sql",
        "/create-user2.sql", "/create-business.sql", "/create-image.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SearchHistoryControllerTest {
    private static final String PATH_HISTORY = "/api/user/history";
    private static final String BUSINESS_EMAIL = "bvg@mail.com";
    private static final String USER_EMAIL="svg@mail.com";

    @Autowired
    private AdviserUserDetailRepository userRepository;
    @Autowired
    EncoderService encoderService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private SearchHistoryService searchHistoryService;
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private HistoryBusinessMapper historyBusinessMapper;

    public static HistoryPointDto historyPointDto;
    public static BusinessShortDto businessShortDto;
    public static List<BusinessShortDto> businessShortDtoList;
    public static List<Business> businessList;
    private AdviserUserDetails user;
    private AdviserUserDetails businessUser;
    private String jwt;

    @BeforeEach
    public void init() {
        encodeTestUserPassword();
        user = userRepository.findByEmail(USER_EMAIL).get();
        businessUser = userRepository.findByEmail(BUSINESS_EMAIL).get();
        jwt = jwtService.generateAccessToken(user.getId());

        businessList = businessRepository.findAllByBusinessUserId(businessUser.getId());
        businessShortDtoList = historyBusinessMapper.toBusinessShortDtoList(businessList);
        businessShortDto = businessShortDtoList.get(0);

        historyPointDto = searchHistoryService.saveHistoryPoint(
                new HistoryPointDto(user.getId(), businessShortDtoList, LocalDateTime.now()));

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
}
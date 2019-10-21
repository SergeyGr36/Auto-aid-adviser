/*
package com.hillel.evo.adviser.userprofile.controller;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = {"/create-user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void activationReturnUserDto() {
        String url = "/user/activation/asdf-1234-qwer";
        //when
        ResponseEntity<UserDto> responseEntity = restTemplate.getForEntity(url, UserDto.class);
        UserDto dto = responseEntity.getBody();
        //then
        Assert.assertNotNull(dto);
    }

    @Test
    public void activationReturnCode404() {
        String url = "/user/activation/asdf-1234-zzzz";
        //when
        ResponseEntity<UserDto> responseEntity = restTemplate.getForEntity(url, UserDto.class);
        //then
        Assert.assertEquals(responseEntity.getStatusCodeValue(), 404);
    }

    @Test
    public void registrationReturnCode201() {
        // given
        RegistrationDto dto = new RegistrationDto("tt@mail.com", "12345", RoleUser.ROLE_USER);
        //when
        ResponseEntity<HashMap> responseEntity = restTemplate.postForEntity("/user/registration", dto, HashMap.class);
        HashMap map = responseEntity.getBody();
        //then
        Assert.assertNotNull(map.get("code"));
    }

    @Test
    public void registrationReturnCode208() {
        // given
        RegistrationDto dto = new RegistrationDto("vg@mail.com", "12345", RoleUser.ROLE_USER);
        //when
        ResponseEntity<HashMap> responseEntity = restTemplate.postForEntity("/user/registration", dto, HashMap.class);
        //then
        Assert.assertEquals(responseEntity.getStatusCodeValue(), 208);
    }


} */
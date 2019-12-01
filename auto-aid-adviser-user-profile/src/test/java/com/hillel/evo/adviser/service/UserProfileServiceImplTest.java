package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.UserProfileStarter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserProfileStarter.class)
@Sql(value = {"/clean-user-profile.sql", "/user-profile.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserProfileServiceImplTest {

//    private Long userId;
//    private UserProfileDto dto;
//    private SimpleUser user;
//    @Autowired
//    private UserProfileServiceImpl service;
//    @Autowired
//    private AdviserUserDetailRepository userRepository;
//    @Autowired
//    private UserProfileRepository repository;
//    @BeforeEach
//    private void createDto(){
//        userId = userRepository.findByEmail("svg@mail.com").get().getId();
//        user=new SimpleUser();
//        user.setId(userId);
//        user.setFirstName("testName");
//        user.setLastName("testLastName");
//        dto = new UserProfileDto();
////        dto.setId(5L);
//    }
//    @Test
//    public void whenCreateUserProfileThenReturnDto(){
//        UserProfileDto test = service.createUserProfile(dto, userId);
//       assertEquals(dto.getId(), test.getId());
//    }
//    @Test
//    public void whenUpdateUserProfileThenReturnDto(){
//        UserProfileDto test = service.updateUserProfile(dto, userId);
//        assertEquals(dto.getId(), test.getId());
//    }
}

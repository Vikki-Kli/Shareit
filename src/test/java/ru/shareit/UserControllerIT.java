package ru.shareit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.shareit.user.User;
import ru.shareit.user.UserDto;
import ru.shareit.user.UserRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    @Qualifier("userRepositoryJPA")
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @BeforeAll
    public void initDB() throws Exception {
        User user1 = new User();
        User user2 = new User();

        user1.setEmail("upupu@mail.ru");
        user1.setName("Ulyana Pushkina");
        userRepository.save(user1);

        user2.setEmail("ololo@mail.ru");
        user2.setName("Olga Lopatina");
        userRepository.save(user2);
    }

    @Test
    public void createUser_regular() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("new@gmail.ru");
        userDto.setName("Newt");

        mockMvc.perform(post("/users")
                .content(mapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.name").value(userDto.getName()));
    }

    @Test
    public void editUser_regular() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("new@gmail.ru");
        userDto.setName("Newt");

        mockMvc.perform(put("/users/{id}", 1)
                .content(mapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.name").value(userDto.getName()));
    }

    @Test
    public void findAll_regular() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getUser_regular() throws Exception {
        User user = userRepository.findById(1).get();

        mockMvc.perform(get("/users/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    public void deleteUser_regular() throws Exception {
        mockMvc.perform(delete("/users/{id}", 2))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

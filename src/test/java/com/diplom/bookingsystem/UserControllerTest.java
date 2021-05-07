package com.diplom.bookingsystem;

import com.diplom.bookingsystem.config.SecurityConfiguration;
import com.diplom.bookingsystem.dto.User.UserDto;
import com.diplom.bookingsystem.model.Address;
import com.diplom.bookingsystem.service.User.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void whenValidInputUserRegistrationReturns200() throws Exception {
        UserDto user = new UserDto();
        user.setUsername("user");
        user.setPassword("password");
        user.setEmail("email@ex.com");
        user.setName("user");
        user.setSurname("userSurname");
        user.setPhone("+380631234567");
        user.setRole(new HashSet<>(Collections.singletonList("user")));
        Address address = new Address();
        address.setCountry("country");
        address.setCity("city");
        address.setStreet("street");
        address.setHouse_number("houseNumber");
        user.setAddress(address);

        mockMvc.perform(post("/user/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }
}

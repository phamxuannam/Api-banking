package com.phmxnnam.prj_banking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.phmxnnam.prj_banking.dto.request.UserCreationRequest;
import com.phmxnnam.prj_banking.dto.response.UserResponse;
import com.phmxnnam.prj_banking.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IUserService userService;

    private UserCreationRequest request;
    private UserResponse userResponse;

    @BeforeEach
    void initData(){
//        List<String> role = new ArrayList<>();
//        role.add("customer");

        request = UserCreationRequest.builder()
                .username("phamxuannam18")
                .password("123456789")
                .customer_id("6d1ccbd3f49c")
                .build();

        userResponse = UserResponse.builder()
                .username("phamxuannam18")
                .customer_id("6d1ccbd3f49c")
                .status(1)
                .build();

    }

    @Test
    void createUser_validRequest_success() throws Exception {
        //GIVEN: request, response in innitData()
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());//read time if variable is local date

        String content = objectMapper.writeValueAsString(request);

        when(userService.create(ArgumentMatchers.any())).thenReturn(userResponse);

        //WHEN
        mockMvc.perform( MockMvcRequestBuilders.post("/api/users")
                                              .contentType(MediaType.APPLICATION_JSON_VALUE)
                                              .content(content) )
                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("result.customer_id")
                        .value("6d1ccbd3f49c"));

        //THEN
    }

    @Test
    void createUser_usernameInvalid_fail() throws Exception {
        //GIVEN: request, response in innitData()
        request.setUsername("pxn");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

//      when(userService.create(ArgumentMatchers.any())).thenReturn(userResponse);

        //WHEN
        mockMvc.perform( MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content) )
                //THEN
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message")
                        .value("must be at least 8 characters."));

        //THEN
    }
}

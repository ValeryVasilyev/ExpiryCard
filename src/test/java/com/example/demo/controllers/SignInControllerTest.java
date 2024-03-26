package com.example.demo.controllers;

import com.example.demo.models.Customer;
import com.example.demo.repo.CustomerRepository;
import com.example.demo.service.CardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignInController.class)
class SignInControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void openIdentificationPage() throws Exception {
        Customer customer = new Customer("username");
        Mockito.when(customerRepository.findByLogin("username")).thenReturn(customer);
        MvcResult mvcResult = mvc.perform(get("/")).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals(mvcResult.getModelAndView().getViewName(), "sign-in");
    }

    @Test
    void handleInvalidLogin() throws Exception {
        String emptyLogin = "";
        String loginWithSpace = " ";
        String errorText = "Введите логин без пробелов";
        List<String> errors = Arrays.asList(emptyLogin, loginWithSpace);
        for (String error : errors) {
            MvcResult mvcResult = mvc.perform(post("/").param("login", error)).andReturn();
            Assertions.assertEquals(mvcResult.getModelAndView().getModel().get("errorText"), errorText);
        }
    }
}